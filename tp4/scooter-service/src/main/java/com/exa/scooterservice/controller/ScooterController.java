package com.exa.scooterservice.controller;

import com.exa.scooterservice.dto.NearbyDTO;
import com.exa.scooterservice.dto.StateUpdateDTO;
import com.exa.scooterservice.dto.TelemetryDTO;
import com.exa.scooterservice.model.Scooter;
import com.exa.scooterservice.service.ScooterService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scooters")
public class ScooterController {
    @Autowired
    private ScooterService service;

    @PostMapping
    public ResponseEntity<Scooter> create(@RequestBody Scooter scooter) {
        Scooter createdScooter = service.createScooter(scooter);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdScooter); // 201 Created
    }

    @GetMapping
    public ResponseEntity<List<Scooter>> getAll() {
        List<Scooter> scooters = service.getAllScooters();
        return ResponseEntity.ok(scooters); // 200 OK
    }

    @GetMapping("/{id}")
    public ResponseEntity<Scooter> getById(@PathVariable Long id) {
        return service.getScooterById(id)
                .map(scooter -> ResponseEntity.ok(scooter))  // 200 OK
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());  // 404 Not Found
    }

    @PutMapping("/{id}")
    public ResponseEntity<Scooter> update(@PathVariable Long id, @Valid @RequestBody Scooter scooter) {
        Scooter updatedScooter = service.updateScooter(id, scooter);
        return ResponseEntity.ok(updatedScooter);  // 200 OK
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            service.deleteScooter(id);
            return ResponseEntity.ok().build(); // 200 OK si la eliminación fue exitosa
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found si el scooter no existe
        }
    }

    @PatchMapping("/{id}/state")
    public ResponseEntity<Scooter> updateState(@PathVariable Long id, @RequestBody StateUpdateDTO dto) {
        Scooter updatedScooter = service.updateState(id, dto.getState());
        return ResponseEntity.ok(updatedScooter); // 200 OK
    }

    @PatchMapping("/{id}/telemetry")
    public ResponseEntity<Scooter> updateTelemetry(@PathVariable Long id, @RequestBody TelemetryDTO dto) {
        Scooter updatedScooter = service.updateTelemetry(id, dto);
        return ResponseEntity.ok(updatedScooter); // 200 OK
    }

    @GetMapping("/nearby")
    public ResponseEntity<List<NearbyDTO>> findNearby(
            @RequestParam @NotNull @DecimalMin(value = "-90") @DecimalMax(value = "90") Double lat,
            @RequestParam @NotNull @DecimalMin(value = "-180") @DecimalMax(value = "180") Double lon,
            @RequestParam @NotNull @Positive Double radius) {

        List<NearbyDTO> nearby = service.findNearby(lat, lon, radius);
        return ResponseEntity.ok(nearby); // 200 OK
    }
}
