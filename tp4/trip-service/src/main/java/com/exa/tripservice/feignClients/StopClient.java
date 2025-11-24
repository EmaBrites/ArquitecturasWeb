package com.exa.tripservice.feignClients;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
@FeignClient(name = "stop-service", url = "http://localhost:8086")
public interface StopClient {

    @GetMapping("/stops/validate")
    Boolean validateStop(@RequestParam("latitude") Double latitude,
                         @RequestParam("longitude") Double longitude);
}