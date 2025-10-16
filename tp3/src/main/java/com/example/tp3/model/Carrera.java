package com.example.tp3.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Carrera {
    @Id
    private int id;
    private String nombre;
    private int duracion;
}
