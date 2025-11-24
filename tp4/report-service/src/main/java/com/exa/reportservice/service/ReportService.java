package com.exa.reportservice.service;

import com.exa.reportservice.dto.*;
import com.exa.reportservice.feign.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final TripClient tripClient;
    private final ScooterClient scooterClient;
    private final AccountClient accountClient;

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

    public List<AccountTransactionDTO> getTransactions(LocalDate startDate, LocalDate endDate) {
        return accountClient.getTransactions(startDate.format(FORMATTER), endDate.format(FORMATTER));
    }

    public Double getTotalRevenue(LocalDate startDate, LocalDate endDate) {
        return accountClient.getTotalRevenue(startDate.format(FORMATTER), endDate.format(FORMATTER));
    }
}
