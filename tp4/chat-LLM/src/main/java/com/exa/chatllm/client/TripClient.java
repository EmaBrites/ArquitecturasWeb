package com.exa.chatllm.client;

import com.exa.chatllm.dto.TripDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "tripClient", url = "${trip.api.url}")
public interface TripClient {

    @GetMapping("/v3/api-docs")
    String getApiDocs();

    @GetMapping("/trips")
    ResponseEntity<List<TripDTO>> getTrips();
}
