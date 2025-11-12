
package com.exa.tripservice.feignClients;

import com.exa.tripservice.dto.TransactionDTO;
import com.exa.tripservice.dto.AccountTransactionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "account-ms", url = "${microservices.account}")
public interface AccountClient {

    @PostMapping("/account/transaction/charge")
    AccountTransactionDTO chargeAccount(@RequestBody TransactionDTO transactionDTO);
}
