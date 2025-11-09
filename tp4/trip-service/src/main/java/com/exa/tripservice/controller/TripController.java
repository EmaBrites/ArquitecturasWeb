package com.exa.tripservice.controller;

import com.exa.tripservice.dto.TripDTO;
import com.exa.tripservice.model.Trip;
import com.exa.tripservice.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trips")
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;

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
    public ResponseEntity<Trip> endTrip(@PathVariable Long id, @RequestBody TripDTO dto) {
        Trip trip = tripService.endTrip(id, dto.getEndLat(), dto.getEndLon(), dto.getKilometers());
        return ResponseEntity.ok(trip);
    }
}
