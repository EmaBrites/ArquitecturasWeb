package com.exa.reportservice.feign;

import com.exa.reportservice.dto.AccountTransactionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "account-service", url = "${microservices.account}")
public interface AccountClient {

    @GetMapping("/accounts/transactions/report")
    List<AccountTransactionDTO> getTransactions(@RequestParam String dateAfter, @RequestParam String dateBefore);

    @GetMapping("/accounts/transactions/revenue")
    Double getTotalRevenue(@RequestParam String dateAfter, @RequestParam String dateBefore);
}
