package com.example.tp3.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "estudiante_carrera")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstudianteCarrera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private int id;

    @Column(name = "inscripcion", nullable = false)
    private int inscripcion;

    @Column(name = "graduacion")
    private int graduacion;

    @Column(name = "antiguedad", nullable = false)
    private int antiguedad;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_estudiante", referencedColumnName = "dni", nullable = false)
    private Estudiante estudiante;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_carrera", referencedColumnName = "id", nullable = false)
    private Carrera carrera;
}