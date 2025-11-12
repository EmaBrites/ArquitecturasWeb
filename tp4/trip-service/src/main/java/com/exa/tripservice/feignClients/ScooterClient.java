package com.exa.tripservice.feignClients;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "scooter-ms", url = "${microservices.scooter}")
public interface ScooterClient {

    @PutMapping("/scooters/{id}/status")
    void updateScooterStatus(@PathVariable("id") Long id, @RequestParam("newStatus") String newStatus);
}
