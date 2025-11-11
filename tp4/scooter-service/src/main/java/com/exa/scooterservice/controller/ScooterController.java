package com.exa.scooterservice.controller;

import com.exa.scooterservice.dto.NearbyDTO;
import com.exa.scooterservice.dto.StateUpdateDTO;
import com.exa.scooterservice.dto.TelemetryDTO;
import com.exa.scooterservice.model.Scooter;
import com.exa.scooterservice.service.ScooterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scooters")
public class ScooterController {
    @Autowired
    private ScooterService service;

    @PostMapping
    public Scooter create(@RequestBody Scooter scooter) {
        return service.createScooter(scooter);
    }

    @GetMapping
    public List<Scooter> getAll() {
        return service.getAllScooters();
    }

    @GetMapping("/{id}")
    public Scooter getById(@PathVariable Long id) {
        return service.getScooterById(id)
                .orElseThrow(() -> new RuntimeException("Scooter not found"));
    }

    @PutMapping("/{id}")
    public Scooter update(@PathVariable Long id, @RequestBody Scooter scooter) {
        return service.updateScooter(id, scooter);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteScooter(id);
    }

    @PatchMapping("/{id}/state")
    public Scooter updateState(@PathVariable Long id, @RequestBody StateUpdateDTO dto) {
        return service.updateState(id, dto.getState());
    }

    @PatchMapping("/{id}/telemetry")
    public Scooter updateTelemetry(@PathVariable Long id, @RequestBody TelemetryDTO dto) {
        return service.updateTelemetry(id, dto);
    }

    @GetMapping("/nearby")
    public List<NearbyDTO> findNearby(
            @RequestParam Double lat,
            @RequestParam Double lon,
            @RequestParam Double radius) {
        return service.findNearby(lat, lon, radius);
    }
}
