package com.exa.tripservice.controller;

import com.exa.tripservice.dto.TripDTO;
import com.exa.tripservice.dto.TripEndDTO;
import com.exa.tripservice.model.Trip;
import com.exa.tripservice.service.TripService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/trips")
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;

    @GetMapping
    @Operation(summary = "Get all trips")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of trips")
    })
    public ResponseEntity<List<Trip>> getTrips() {
        return ResponseEntity.ok(tripService.getTrips());
    }

    @PostMapping
    @Operation(summary = "Start a new trip")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trip started successfully")
    })
    public ResponseEntity<Trip> startTrip(@RequestBody TripDTO dto) {
        Trip trip = tripService.startTrip(dto);
        return ResponseEntity.ok(trip);
    }

    //  Pausar viaje
    @PostMapping("/{id}/pause")
    @Operation(summary = "Pause a trip")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trip paused successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid trip state"),
            @ApiResponse(responseCode = "404", description = "Trip not found")
    })
    public ResponseEntity<Trip> pauseTrip(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(tripService.pauseTrip(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    //  Reanudar viaje
    @PostMapping("/{id}/resume")
    @Operation(summary = "Resume a paused trip")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trip resumed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid trip state"),
            @ApiResponse(responseCode = "404", description = "Trip not found")
    })
    public ResponseEntity<Trip> resumeTrip(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(tripService.resumeTrip(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/end")
    @Operation(summary = "End a trip")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trip ended successfully"),
            @ApiResponse(responseCode = "404", description = "Trip not found")
    })
    public ResponseEntity<Trip> endTrip(@PathVariable Long id, @RequestBody TripEndDTO dto) {
        try {
            Trip trip = tripService.endTrip(id, dto.getEndLat(), dto.getEndLon(), dto.getKilometers());
            return ResponseEntity.ok(trip);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //Consultar viaje
    @GetMapping("/filtered")
    @Operation(summary = "Get trips filtered by accountId, scooterId, and date range")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved filtered list of trips")
    })
    public ResponseEntity<List<Trip>> getTripsFiltered(
            @RequestParam(required = false) Long accountId,
            @RequestParam(required = false) Long scooterId,
            @Parameter(description = "Start date-time in ISO format (yyyy-MM-dd'T'HH:mm:ss)")
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @Parameter(description = "End date-time in ISO format (yyyy-MM-dd'T'HH:mm:ss)")
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        List<Trip> trips = tripService.getTripsFiltered(accountId, scooterId, from, to);
        return ResponseEntity.ok(trips);
    }
}
