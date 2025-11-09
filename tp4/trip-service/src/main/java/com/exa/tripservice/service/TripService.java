package com.exa.tripservice.service;

import com.exa.tripservice.model.Trip;
import com.exa.tripservice.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    // 👇 Inyectamos las URLs del properties
    @Value("${microservices.account}")
    private String accountMsUrl;

    @Value("${microservices.scooter}")
    private String scooterMsUrl;

    public Trip startTrip(Long accountId, Long scooterId, Double startLat, Double startLon) {

        System.out.println("Usando Account MS URL: " + accountMsUrl);
        System.out.println("Usando Scooter MS URL: " + scooterMsUrl);

        // Por ahora podés simular hasta que existan los endpoints:
        boolean accountActive = true;
        double balance = 100.0;
        boolean scooterAvailable = true;

        if (!accountActive)
            throw new RuntimeException("Cuenta bloqueada o inactiva");
        if (balance < 50.0)
            throw new RuntimeException("Saldo insuficiente");
        if (!scooterAvailable)
            throw new RuntimeException("Scooter no disponible");

        Trip trip = new Trip();
        trip.setAccountId(accountId);
        trip.setScooterId(scooterId);
        trip.setStartLat(startLat);
        trip.setStartLon(startLon);
        trip.setStartTime(LocalDateTime.now());
        trip.setStatus("ACTIVE");

        return tripRepository.save(trip);
    }

    public List<Trip> getTripsByAccount(Long accountId) {
        return tripRepository.findByAccountId(accountId);
    }
}
