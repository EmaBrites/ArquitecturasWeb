package com.exa.chatllm.client;

import com.exa.chatllm.dto.AccountDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "account-client", url = "${account.api.url}")
public interface AccountClient {

    @GetMapping("/accounts/{id}")
    ResponseEntity<AccountDTO> getAccountById(@PathVariable Integer id);
}
