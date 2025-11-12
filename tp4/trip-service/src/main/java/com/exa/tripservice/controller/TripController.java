package com.exa.tripservice.controller;

import com.exa.tripservice.dto.TripDTO;
import com.exa.tripservice.model.Trip;
import com.exa.tripservice.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/trips")
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;

    @PostMapping
    public ResponseEntity<Trip> startTrip(@RequestBody TripDTO dto) {
        Trip trip = tripService.startTrip(dto);
        return ResponseEntity.ok(trip);
    }

    //  Pausar viaje
    @PostMapping("/{id}/pause")
    public ResponseEntity<Trip> pauseTrip(@PathVariable Long id) {
        return ResponseEntity.ok(tripService.pauseTrip(id));
    }

    //  Reanudar viaje
    @PostMapping("/{id}/resume")
    public ResponseEntity<Trip> resumeTrip(@PathVariable Long id) {
        return ResponseEntity.ok(tripService.resumeTrip(id));
    }

    // Finalizar viaje
    @PostMapping("/{id}/end")
    public ResponseEntity<Trip> endTrip(
            @PathVariable Long id,
            @RequestParam Double endLat,
            @RequestParam Double endLon,
            @RequestParam Double kilometers) {

        Trip trip = tripService.endTrip(id, endLat, endLon, kilometers);
        return ResponseEntity.ok(trip);
    }

    //Consultar viaje
    @GetMapping
    public ResponseEntity<List<Trip>> getTripsFiltered(
            @RequestParam(required = false) Long accountId,
            @RequestParam(required = false) Long scooterId,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {

        List<Trip> trips = tripService.getTripsFiltered(accountId, scooterId, from, to);
        return ResponseEntity.ok(trips);
    }
}
