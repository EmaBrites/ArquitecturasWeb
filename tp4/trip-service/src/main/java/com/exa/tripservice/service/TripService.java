package com.exa.tripservice.service;
import com.exa.tripservice.dto.TransactionDTO;
import com.exa.tripservice.dto.TripDTO;
import com.exa.tripservice.feignClients.AccountClient;
import com.exa.tripservice.feignClients.ScooterClient;
import com.exa.tripservice.feignClients.StopClient;
import com.exa.tripservice.model.Trip;
import com.exa.tripservice.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;
    private final AccountClient accountClient;
    private final ScooterClient scooterClient;
    private final StopClient stopClient;

    // US-TRIP-01
    public Trip startTrip(TripDTO dto) {
        Trip trip = new Trip();
        trip.setAccountId(dto.getAccountId());
        trip.setScooterId(dto.getScooterId());
        trip.setStartLat(dto.getStartLat());
        trip.setStartLon(dto.getStartLon());
        trip.setStartTime(LocalDateTime.now());
        trip.setStatus("ACTIVE");
        return tripRepository.save(trip);
    }

    // US-TRIP-02
    public Trip pauseTrip(Long tripId) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Viaje no encontrado"));
        if (!"ACTIVE".equals(trip.getStatus()))
            throw new RuntimeException("Solo se pueden pausar viajes activos");

        trip.setPauseTime(LocalDateTime.now());
        trip.setStatus("PAUSED");
        trip.setLongPause(false);
        return tripRepository.save(trip);
    }

    public Trip resumeTrip(Long tripId) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Viaje no encontrado"));
        if (!"PAUSED".equals(trip.getStatus()))
            throw new RuntimeException("Solo se pueden reanudar viajes pausados");

        Duration pauseDuration = Duration.between(trip.getPauseTime(), LocalDateTime.now());
        if (pauseDuration.toMinutes() > 15) trip.setLongPause(true);

        trip.setPauseTime(null);
        trip.setStatus("ACTIVE");
        return tripRepository.save(trip);
    }

    // US-TRIP-03 + US-TRIP-05
    public Trip endTrip(Long tripId, Double endLat, Double endLon, Double kilometers) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Viaje no encontrado"));

        if (!"ACTIVE".equals(trip.getStatus()))
            throw new RuntimeException("Solo se pueden finalizar viajes activos");

        //  Validar parada (Stop MS)
        Boolean validStop = stopClient.validateStop(endLat, endLon);
        if (Boolean.FALSE.equals(validStop))
            throw new RuntimeException("No estás en una parada válida");

        //  Cargar datos finales
        trip.setEndLat(endLat);
        trip.setEndLon(endLon);
        trip.setEndTime(LocalDateTime.now());
        trip.setKilometers(kilometers);
        Duration duration = Duration.between(trip.getStartTime(), trip.getEndTime());
        trip.setDurationMinutes(duration.toMinutes());

        //  Cambiar estado
        trip.setStatus("FINISHED");

        // Notificar Scooter MS
        try {
            scooterClient.updateScooterStatus(trip.getScooterId(), "AVAILABLE");
        } catch (Exception e) {
            System.err.println("️ Error al actualizar scooter: " + e.getMessage());
        }

        // 5 Cobrar al Account MS (US-TRIP-05)
        double pricePerKm = 30.0;
        double total = kilometers * pricePerKm;

        try {
            TransactionDTO transaction = new TransactionDTO();
            transaction.setAccountId(trip.getAccountId());
            transaction.setAmount(total);
            transaction.setDescription("Trip charge for trip ID " + trip.getId());

            accountClient.chargeAccount(transaction);
        } catch (Exception e) {
            trip.setStatus("BILLING_ERROR");
            System.err.println(" Error al facturar cuenta: " + e.getMessage());
        }
        return trip;
    }

    // US-TRIP-04
    public List<Trip> getTripsFiltered(Long accountId, Long scooterId, LocalDateTime from, LocalDateTime to) {
        return tripRepository.findFilteredTrips(accountId, scooterId, from, to);
    }
}