package com.exa.reportservice.service;

import com.exa.reportservice.dto.*;
import com.exa.reportservice.feign.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final TripClient tripClient;
    private final ScooterClient scooterClient;

    public List<ScooterReportDTO> getScootersWithMinTrips(int year, int minTrips) {
        List<ScooterTripReportDTO> trips = tripClient.getTripsCountByScooter(year);

        return trips.stream()
                .filter(t -> t.getTripCount() >= minTrips)
                .map(t -> {
                    ScooterDTO scooter = scooterClient.getScooterById(t.getScooterId());
                    return new ScooterReportDTO(
                            scooter.getId(),
                            scooter.getModel(),
                            t.getTripCount()
                    );
                })
                .collect(Collectors.toList());
    }
}
