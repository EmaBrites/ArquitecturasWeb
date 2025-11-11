package com.exa.scooterservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "scooters")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Scooter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true)
    private String serial;

    private Double latitude;
    private Double longitude;

    @Enumerated(EnumType.STRING)
    private ScooterState state;

    private Double accumulatedKms = 0.0;
    private Long accumulatedMinutes = 0L;

    @Version
    private Long version;
}
