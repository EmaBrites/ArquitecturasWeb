package com.exa.tripservice.controller;

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

    // 🔸 Pausar viaje
    @PostMapping("/{id}/pause")
    public ResponseEntity<Trip> pauseTrip(@PathVariable Long id) {
        return ResponseEntity.ok(tripService.pauseTrip(id));
    }

    // 🔸 Reanudar viaje
    @PostMapping("/{id}/resume")
    public ResponseEntity<Trip> resumeTrip(@PathVariable Long id) {
        return ResponseEntity.ok(tripService.resumeTrip(id));
    }
}
