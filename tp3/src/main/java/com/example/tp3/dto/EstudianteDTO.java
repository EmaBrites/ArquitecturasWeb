package com.example.tp3.dto;

import lombok.*;

import java.io.Serializable;
@Data
@AllArgsConstructor
@Builder
@ToString

public class EstudianteDTO implements Serializable {
    private final String nombre;
    private final String apellido;
    private final int edad;
    private final String genero;
    private final int numeroLibreta;
    private final String ciudad;
    private final int dni;

    public int getEdad() {
        return edad;
    }

    public int getDni() {
        return dni;
    }
}