package com.exa.tripservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripEndDTO {
    private double endLat;
    private double endLon;
    private Double kilometers;
}
