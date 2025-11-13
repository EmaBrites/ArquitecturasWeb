package com.exa.tripservice.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long accountId;
    private Long scooterId;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private Double startLat;
    private Double startLon;
    private Double endLat;
    private Double endLon;

    private Double kilometers;
    private Long durationMinutes;
    private String status; // ACTIVE, PAUSED, FINISHED
    private LocalDateTime pauseTime;
    private Boolean longPause;
}
