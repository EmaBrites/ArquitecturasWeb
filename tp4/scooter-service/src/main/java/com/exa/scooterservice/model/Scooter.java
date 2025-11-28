package com.exa.scooterservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "scooters")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Scooter {
    @Id
    private String id;

    private String serial;

    private Double latitude;
    private Double longitude;

    private ScooterState state;

    private Double accumulatedKms = 0.0;
    private Long accumulatedMinutes = 0L;

    private Long version;
}
