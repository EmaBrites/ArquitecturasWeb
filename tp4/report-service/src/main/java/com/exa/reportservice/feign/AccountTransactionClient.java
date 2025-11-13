package com.exa.reportservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@FeignClient(name = "account-service", url = "${microservices.account}")
public interface AccountTransactionClient {
    @GetMapping("/total-billed")
    double getTotalBilledInPeriod(@RequestParam("dateAfter")LocalDate dateAfter,
                                  @RequestParam("dateBefore") LocalDate dateBefore);
}
