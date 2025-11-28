package com.exa.tripservice.dto;

import lombok.Data;

@Data
public class TripDTO {
    private Long accountId;
    private String scooterId;
    private Double startLat;
    private Double startLon;
    private Double endLat;
    private Double endLon;
    private Double kilometers;
}
