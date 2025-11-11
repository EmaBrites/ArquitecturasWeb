package com.exa.scooterservice.dto;

import lombok.Data;

@Data
public class TelemetryDTO {
    private Double latitude;
    private Double longitude;
    // cambios a sumar al acumulado
    private Double kmDelta;        // puede ser 0.0
    private Long minutesDelta;     // puede ser 0L
}
