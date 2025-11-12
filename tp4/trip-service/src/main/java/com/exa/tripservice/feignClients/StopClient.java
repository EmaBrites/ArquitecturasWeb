package com.exa.tripservice.feignClients;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "stop-ms", url = "${microservices.stop}")
public interface StopClient {

    @GetMapping("/stops/validate")
    Boolean validateStop(@RequestParam("lat") Double lat, @RequestParam("lon") Double lon);
}
