package com.exa.stopservice.dto;

import com.exa.stopservice.entity.Stop;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateStopDTO {
    private double latitude;
    private double longitude;

    public CreateStopDTO(Stop stop){
        this.latitude = stop.getLatitude();
        this.longitude = stop.getLongitude();
    }
}
