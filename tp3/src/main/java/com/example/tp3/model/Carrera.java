package com.example.tp3.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "carrera")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Carrera {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private int id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "duracion", nullable = false)
    private int duracion;
}