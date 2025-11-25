package com.exa.accountservice.feign;

import com.exa.accountservice.dto.mp.PaymentRequestDTO;
import com.exa.accountservice.dto.mp.PaymentResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "mp-mock-service", url = "http://localhost:8004/mp")
public interface MpMockFeignClient {

    @PostMapping("/pay")
    PaymentResponseDTO processPayment(@RequestBody PaymentRequestDTO request);
}
