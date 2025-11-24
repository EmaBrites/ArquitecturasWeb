package com.exa.scooterservice.dto;

import com.exa.scooterservice.model.ScooterState;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateScooterDTO {
    private String serial;

    private Double latitude;
    private Double longitude;

    private ScooterState state;

    private Double accumulatedKms = 0.0;
    private Long accumulatedMinutes = 0L;
}
