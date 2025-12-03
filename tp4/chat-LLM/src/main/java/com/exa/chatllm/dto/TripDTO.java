package com.exa.chatllm.dto;

import java.time.LocalDateTime;

/**
 * @param status ACTIVE, PAUSED, FINISHED
 */
public record TripDTO(
        Long id,
        Long accountId,
        String scooterId,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Double startLat,
        Double startLon,
        Double endLat,
        Double endLon,
        Double kilometers,
        Long durationMinutes,
        String status,
        Long pauseDuration,
        LocalDateTime pauseTime,
        Boolean longPause) {
}
