package com.exa.tripservice.controller;

import com.exa.tripservice.model.Trip;
import com.exa.tripservice.service.TripService;
import com.exa.tripservice.dto.TripDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trips")
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;

    // US-TRIP-01: Iniciar viaje
    @PostMapping
    public ResponseEntity<Trip> startTrip(@RequestBody TripDTO dto) {
        Trip trip = tripService.startTrip(
                dto.getAccountId(),
                dto.getScooterId(),
                dto.getStartLat(),
                dto.getStartLon()
        );
        return ResponseEntity.ok(trip);
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<Trip>> getTripsByAccount(@PathVariable Long accountId) {
        return ResponseEntity.ok(tripService.getTripsByAccount(accountId));
    }
}
