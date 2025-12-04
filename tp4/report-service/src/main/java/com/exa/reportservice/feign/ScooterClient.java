package com.exa.reportservice.feign;

import com.exa.reportservice.dto.ScooterDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "scooter-service", url = "${microservices.scooter}")
public interface ScooterClient {

    @GetMapping("/scooters/{id}")
    ScooterDTO getScooterById(@PathVariable Long id);

    @GetMapping("/scooters/batch")
    List<ScooterDTO> getScootersByIds(@RequestParam List<Integer> ids);
}
