package com.exa.reportservice.feign;

import com.exa.reportservice.dto.ScooterTripReportDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@FeignClient(name = "trip-service", url = "${microservices.trip}")
@RequestMapping("/trips")
public interface TripClient {

    @GetMapping("/report")
    List<ScooterTripReportDTO> getTripsCountByScooter(@RequestParam int year);

    @GetMapping("/scooters/min-trips")
    List<Long> getScootersWithMinTripsByYear(@RequestParam int year, @RequestParam int minTrips);
}
