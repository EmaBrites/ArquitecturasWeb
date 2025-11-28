package com.exa.scooterservice.controller;

import com.exa.scooterservice.Exception.NotFoundException;
import com.exa.scooterservice.dto.*;
import com.exa.scooterservice.service.ScooterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/scooters")
public class ScooterController {
    private final ScooterService scooterService;

    public ScooterController(ScooterService scooterService){this.scooterService = scooterService;}

    @PostMapping
    @Operation(summary = "Create a new scooter")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "201", description = "Scooter successfully created", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = ScooterDTO.class)
            ))
    })
    public ResponseEntity<ScooterDTO> create(@RequestBody CreateScooterDTO scooterDTO) { //TODO manejar error serial duplicado, parece pavada pero pasa de id 1 a 3 si haces fallar el 2
        ScooterDTO createdScooter = scooterService.createScooter(scooterDTO);
        URI location = URI.create(String.format("/scooters/%s", createdScooter.getId()));
        return ResponseEntity.created(location).body(createdScooter);
    }

    @GetMapping
    @Operation(summary = "Get all scooters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "List of scooters", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = ScooterDTO.class)
            ))
    })
    public ResponseEntity<List<ScooterDTO>> getAll() {
        List<ScooterDTO> scooters = scooterService.getAllScooters();
        return ResponseEntity.ok(scooters);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get scooter by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Scooter found", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = ScooterDTO.class)
            )),
            @ApiResponse(responseCode = "404", description = "Scooter not found")
    })
    public ResponseEntity<ScooterDTO> getById(@PathVariable String id) {
        try{
            ScooterDTO scooterDTO = scooterService.getScooterById(id);
            return ResponseEntity.ok(scooterDTO);
        }
        catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing Scooter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Scooter updated successfully", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = ScooterDTO.class)
            )),
            @ApiResponse(responseCode = "404", description = "Scooter not found")
    })
    public ResponseEntity<ScooterDTO> update(@PathVariable String id, @Valid @RequestBody CreateScooterDTO scooter) {
        try{
            ScooterDTO updatedScooter = scooterService.updateScooter(id, scooter);
            return ResponseEntity.ok(updatedScooter);
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Scooter by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Scooter deleted successfully", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = ScooterDTO.class)
            )),
            @ApiResponse(responseCode = "404", description = "Scooter not found")
    })
    public ResponseEntity<ScooterDTO> delete(@PathVariable String id) {
        try {
            ScooterDTO deletedScooter = scooterService.deleteScooter(id);
            return ResponseEntity.ok(deletedScooter);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/state") //TODO mensaje de invalid-state
    @Operation(summary = "Update a Scooter's state")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Scooter's state successfully updated",content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = ScooterDTO.class)
            )),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Scooter not found")
    })
    public ResponseEntity<ScooterDTO> updateState(@PathVariable String id, @RequestBody StateUpdateDTO dto) {
        try{
            ScooterDTO updatedScooter = scooterService.updateState(id, dto.getState());
            return ResponseEntity.ok(updatedScooter);
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/telemetry")
    @Operation(summary = "Update a Scooter's telemetry")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Scooter's telemetry successfully updated", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = ScooterDTO.class)
            )),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Scooter not found")
    })
    public ResponseEntity<ScooterDTO> updateTelemetry(@PathVariable String id, @RequestBody TelemetryDTO dto) {
        try{
            ScooterDTO updatedScooter = scooterService.updateTelemetry(id, dto);
            return ResponseEntity.ok(updatedScooter);
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/nearby")
    @Operation(summary = "Find nearby Scooters within a radius")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "List of scooters", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = ScooterDTO.class)
            )),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    public ResponseEntity<List<NearbyDTO>> findNearby( //TODO acá ya habia uso de DTO, dejé as-is
            @RequestParam @NotNull @DecimalMin(value = "-90") @DecimalMax(value = "90") Double lat,
            @RequestParam @NotNull @DecimalMin(value = "-180") @DecimalMax(value = "180") Double lon,
            @RequestParam @NotNull @Positive Double radius) {

        List<NearbyDTO> nearby = scooterService.findNearby(lat, lon, radius);
        return ResponseEntity.ok(nearby);
    }

    @GetMapping("/batch")
    @Operation(summary = "Get scooters by a list of IDs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "List of scooters", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = ScooterDTO.class)
            ))
    })
    public ResponseEntity<List<ScooterDTO>> getScootersByIds(@RequestParam List<String> ids) {
        List<ScooterDTO> scooters = scooterService.getScootersByIds(ids);
        return ResponseEntity.ok(scooters);
    }
}
