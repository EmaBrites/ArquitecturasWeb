package com.exa.reportservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScooterReportDTO {
    private Long scooterId;
    private String model;
    private Long tripCount;
}
