package com.example.tp3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@Builder


public class CarreraInscriptosDTO {
    private String nombreCarrera;
    private Long cantidadInscriptos;
}