package com.exa.userservice.feignClients;

import com.exa.userservice.dto.AccountDTO;
import com.exa.userservice.dto.CreateAccountDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "account-service", url = "http://localhost:8001")
public interface AccountFeignClients {

    @PostMapping("/account")
    ResponseEntity<AccountDTO> createAccount(@RequestBody CreateAccountDTO dto);

    @GetMapping("/account/{id}")
    ResponseEntity<AccountDTO> getAccountById(@PathVariable Integer id);

    @DeleteMapping("/account/{id}")
    ResponseEntity<Void> deleteAccount(@PathVariable Integer id);
}
