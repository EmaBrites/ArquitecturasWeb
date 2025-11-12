package com.exa.scooterservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class TelemetryDTO {
    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    // cambios a sumar al acumulado
    @PositiveOrZero
    private Double kmDelta;        // puede ser 0.0

    @PositiveOrZero
    private Long minutesDelta;     // puede ser 0L
}
