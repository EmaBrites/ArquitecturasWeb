package com.exa.reportservice.controller;

import com.exa.reportservice.dto.AccountTransactionDTO;
import com.exa.reportservice.dto.ScooterReportDTO;
import com.exa.reportservice.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/admin/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/scooters")
    @Operation(summary = "Get scooters with minimum trips in a given year")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved scooter report")
    })
    public ResponseEntity<List<ScooterReportDTO>> getScooterReport(
            @RequestParam int year,
            @RequestParam int minTrips) {
        return ResponseEntity.ok(reportService.getScootersWithMinTrips(year, minTrips));
    }

    @GetMapping("/transactions")
    @Operation(summary = "Get account transactions within a date range")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved transactions report")
    })
    public ResponseEntity<List<AccountTransactionDTO>> getTransactionsReport(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        return ResponseEntity.ok(reportService.getTransactions(startDate, endDate));
    }

    @GetMapping("/transactions/total-revenue")
    @Operation(summary = "Get total revenue within a date range")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved total revenue")
    })
    public ResponseEntity<Double> getTotalRevenue(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        return ResponseEntity.ok(reportService.getTotalRevenue(startDate, endDate));
    }
}
