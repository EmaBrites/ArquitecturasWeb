package com.exa.reportservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScooterTripReportDTO {
    private Long scooterId;
    private Long tripCount;
}
