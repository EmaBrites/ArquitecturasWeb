package com.exa.scooterservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NearbyDTO {
    private Integer id;
    private String serial;
    private Double latitude;
    private Double longitude;
    private Double distanceKm;
}
