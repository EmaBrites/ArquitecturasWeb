package com.exa.scooterservice.dto;

import com.exa.scooterservice.model.Scooter;
import com.exa.scooterservice.model.ScooterState;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ScooterDTO {
    private Integer id;
    private String serial;
    private Double latitude;
    private Double longitude;

    private ScooterState state;
    private Double accumulatedKms = 0.0;
    private Long accumulatedMinutes = 0L;

    public ScooterDTO(Scooter scooter){
        this.id = scooter.getId();
        this.serial = scooter.getSerial();
        this.latitude = scooter.getLatitude();
        this.longitude = scooter.getLongitude();
        this.state = scooter.getState();
        this.accumulatedKms = scooter.getAccumulatedKms();
        this.accumulatedMinutes = scooter.getAccumulatedMinutes();
    }
}
