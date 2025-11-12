package com.exa.tripservice.feignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "account-ms", url = "${microservices.account}")
public interface AccountClient {

    @PutMapping("/accounts/{id}/charge")
    void chargeAccount(@PathVariable("id") Long accountId, @RequestParam("amount") double amount);
}
