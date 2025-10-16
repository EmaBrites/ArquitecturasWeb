package com.example.tp3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
@Data
@AllArgsConstructor
@Builder
@ToString


public class EstudianteCarreraDTO implements Serializable {
    private final int id;
    private final int inscripcion;
    private final int graduacion;
    private final int antiguedad;
    private final int idCarrera;
    private final int idEstudiante;
    private final String nombreEstudiante;
    private final String nombreCarrera;
    private final String apellidoEstudiante;
    private final String generoEstudiante;
    private final int libretaEstudiante;

    public Long getDniEstudiante() {
        return (long) idEstudiante;
    }
}
