package com.exa.reportservice.controller;

import com.exa.reportservice.dto.ScooterReportDTO;
import com.exa.reportservice.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/scooters")
    public ResponseEntity<List<ScooterReportDTO>> getScooterReport(
            @RequestParam int year,
            @RequestParam int minTrips) {

        return ResponseEntity.ok(
                reportService.getScootersWithMinTrips(year, minTrips)
        );
    }
}
