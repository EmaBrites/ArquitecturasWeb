package com.exa.scooterservice.dto;

import com.exa.scooterservice.model.ScooterState;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateScooterDTO {
    private String serial;

    private Double latitude;
    private Double longitude;

    @Enumerated(EnumType.STRING)
    private ScooterState state;

    private Double accumulatedKms = 0.0;
    private Long accumulatedMinutes = 0L;
}
