package com.exa.tripservice.feignClients;


import com.exa.tripservice.dto.StateUpdateDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "scooter-ms", url = "${microservices.scooter}")
public interface ScooterClient {

    @PutMapping("/scooters/{id}/state")
    void updateScooterState(@PathVariable("id") String id, @RequestBody StateUpdateDTO dto);
}

