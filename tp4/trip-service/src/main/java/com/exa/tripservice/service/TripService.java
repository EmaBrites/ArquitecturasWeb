package com.exa.tripservice.service;

import com.exa.tripservice.model.Trip;
import com.exa.tripservice.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;
    private final ExternalServicesClient externalServicesClient;

    // 👇 Inyectamos las URLs del properties
    @Value("${microservices.account}")
    private String accountMsUrl;

    @Value("${microservices.scooter}")
    private String scooterMsUrl;

    // US-TRIP-01
    public Trip startTrip(Long accountId, Long scooterId, Double startLat, Double startLon) {
        Trip trip = new Trip();
        trip.setAccountId(accountId);
        trip.setScooterId(scooterId);
        trip.setStartLat(startLat);
        trip.setStartLon(startLon);
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
        trip.setStatus("ACTIVE");
        trip.setPauseTime(null);
        return tripRepository.save(trip);
    }
    //US-TRIP-03
    public Trip endTrip(Long tripId, Double endLat, Double endLon, Double kilometers) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Viaje no encontrado"));

        if (!"ACTIVE".equals(trip.getStatus())) {
            throw new RuntimeException("Solo se pueden finalizar viajes activos");
        }

        boolean stopValid = externalServicesClient.isStopValid(endLat, endLon);
        if (!stopValid) {
            throw new RuntimeException("No estás en una parada válida para finalizar el viaje");
        }

        trip.setEndLat(endLat);
        trip.setEndLon(endLon);
        trip.setEndTime(LocalDateTime.now());
        trip.setKilometers(kilometers);

        Duration duration = Duration.between(trip.getStartTime(), trip.getEndTime());
        trip.setDurationMinutes(duration.toMinutes());
        trip.setStatus("FINISHED");
        externalServicesClient.setScooterAvailable(trip.getScooterId());
        double pricePerKm = 30.0; // o configurable desde application.properties
        externalServicesClient.chargeAccount(trip.getAccountId(), kilometers * pricePerKm);

        return tripRepository.save(trip);
    }



    public List<Trip> getTripsByAccount(Long accountId) {
        return tripRepository.findByAccountId(accountId);
    }
}
