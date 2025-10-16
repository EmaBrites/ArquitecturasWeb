package com.example.tp3.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstudianteCarrera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int inscripcion;
    private int graduacion;
    private int antiguedad;
    @ManyToOne(optional = false)
    @JoinColumn(name = "idEstudiante" , referencedColumnName = "dni")
    private Estudiante estudiante;
    @ManyToOne(optional = false)
    @JoinColumn(name = "idCarerra" , referencedColumnName = "id")
    private Carrera carrera;
}
