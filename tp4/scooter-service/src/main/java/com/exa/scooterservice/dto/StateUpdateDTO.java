package com.exa.scooterservice.dto;

import com.exa.scooterservice.model.ScooterState;
import lombok.Data;

@Data
public class StateUpdateDTO {
    private ScooterState state;
}
