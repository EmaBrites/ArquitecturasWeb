package com.exa.stopservice.dto;

import com.exa.stopservice.entity.Stop;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class StopDTO implements Serializable {
    private int id;
    private double latitude;
    private double longitude;

    public StopDTO(Stop stop){
        this.id = stop.getId();
        this.latitude = stop.getLatitude();
        this.longitude = stop.getLongitude();
    }
}
