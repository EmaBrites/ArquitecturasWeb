package com.exa.reportservice.feign;

import com.exa.reportservice.dto.ScooterDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "scooter-service", url = "${microservices.scooter}")
@RequestMapping("/scooters")
public interface ScooterClient {

    @GetMapping("/{id}")
    ScooterDTO getScooterById(@PathVariable Long id);

    @GetMapping("/batch")
    List<ScooterDTO> getScootersByIds(@RequestParam List<Integer> ids);
}
