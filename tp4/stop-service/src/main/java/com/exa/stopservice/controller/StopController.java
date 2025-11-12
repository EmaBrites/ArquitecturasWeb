package com.exa.stopservice.controller;

import com.exa.stopservice.Exception.NotFoundException;
import com.exa.stopservice.dto.CreateStopDTO;
import com.exa.stopservice.dto.StopDTO;
import com.exa.stopservice.service.StopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/stop")
public class StopController {

    private final StopService stopService;

    public StopController(StopService stopService){this.stopService=stopService;}

    @PostMapping
    @Operation(summary = "Create a new stop")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Stop created successfully", content = @Content(
                mediaType = "application/json", schema = @Schema(implementation = StopDTO.class)
        ))
    })
    public ResponseEntity<StopDTO> createStop(@RequestBody CreateStopDTO stopDTO){
        StopDTO createdStop = stopService.createStop(stopDTO);
        URI location = URI.create(String.format("/account/%s", createdStop.getId()));
        return ResponseEntity.created(location).body(createdStop);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get stop by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stop found", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = StopDTO.class)
            )),
            @ApiResponse(responseCode = "404", description = "Stop not found")
    })
    public ResponseEntity<StopDTO> getStopById(@PathVariable Integer id){
        try{
            StopDTO stopDTO = stopService.getStopById(id);
            return ResponseEntity.ok(stopDTO);
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/{id}")
    @Operation(summary = "Update an existing stop")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stop updated successfully", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = StopDTO.class)
            )),
            @ApiResponse(responseCode = "404", description = "Stop not found")
    })
    public ResponseEntity<StopDTO> updateStop(@PathVariable Integer id, @RequestBody CreateStopDTO stopDTO){
        try{
            StopDTO updatedStop = stopService.updateStop(id,stopDTO);
            return ResponseEntity.ok(updatedStop);
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a stop by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stop deleted successfully", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = StopDTO.class)
            )),
            @ApiResponse(responseCode = "404", description = "Stop not found")
    })
    public ResponseEntity<StopDTO> deleteStop(@PathVariable Integer id){
        try{
            StopDTO deletedStop = stopService.deleteStop(id);
            return ResponseEntity.ok(deletedStop);
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    @Operation(summary = "Check valid stop by coordinates")
    public boolean checkValidStop(@Param("latitude") double latitude, @Param("longitude") double longitude){
        return stopService.isStop(latitude,longitude);
    }
}
