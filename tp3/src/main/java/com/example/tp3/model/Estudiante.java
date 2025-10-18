package com.example.tp3.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Estudiante{
    private String nombre;
    private String apellido;
    private int edad;
    private String genero;
    @Id
    private int dni;
    private String ciudad;
    private int numeroLibreta;
}