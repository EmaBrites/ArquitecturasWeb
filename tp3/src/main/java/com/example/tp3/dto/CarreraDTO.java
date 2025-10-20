package com.example.tp3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
@Data
@AllArgsConstructor
@Builder



public class CarreraDTO implements Serializable {
    private final int id;
    private final String nombre;
    private final int duracion;

}