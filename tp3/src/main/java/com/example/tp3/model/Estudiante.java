package com.example.tp3.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "estudiante")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Estudiante {

    @Id
    @Column(name = "dni", nullable = false, unique = true)
    private int dni;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellido", nullable = false)
    private String apellido;

    @Column(name = "edad", nullable = false)
    private int edad;

    @Column(name = "genero", nullable = false)
    private String genero;

    @Column(name = "ciudad", nullable = false)
    private String ciudad;

    @Column(name = "numero_libreta", nullable = false, unique = true)
    private int numeroLibreta;
}