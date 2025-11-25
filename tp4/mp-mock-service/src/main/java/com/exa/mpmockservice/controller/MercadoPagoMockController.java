package com.exa.mpmockservice.controller;

import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/mock/mp")
public class MercadoPagoMockController {

    @PostMapping("/preference")
    public ResponseEntity<PreferenceResponse> createPreference(@RequestBody PreferenceRequest request) {
        PreferenceResponse resp = new PreferenceResponse();
        resp.preferenceId = UUID.randomUUID().toString();
        resp.initPoint = "https://fake.mercadopago.com/pay/" + resp.preferenceId;

        return ResponseEntity.ok(resp);
    }

    @GetMapping("/payment-status/{id}")
    public ResponseEntity<PaymentStatusResponse> getPaymentStatus(@PathVariable String id) {
        PaymentStatusResponse resp = new PaymentStatusResponse();
        resp.paymentId = id;
        resp.status = "approved";
        resp.detail = "Mocked payment";

        return ResponseEntity.ok(resp);
    }

    @PostMapping("/confirm/{id}")
    public ResponseEntity<PaymentStatusResponse> confirmPayment(@PathVariable String id) {
        PaymentStatusResponse resp = new PaymentStatusResponse();
        resp.paymentId = id;
        resp.status = "approved";
        resp.detail = "Mock confirmation OK";

        return ResponseEntity.ok(resp);
    }

    @PostMapping("/webhook")
    public ResponseEntity<Void> receiveWebhook(@RequestBody String event) {
        System.out.println("WEBHOOK RECIBIDO: " + event);
        return ResponseEntity.ok().build();
    }

    @Data
    public static class PreferenceRequest {
        private Double amount;
        private String description;
    }

    @Data
    public static class PreferenceResponse {
        public String preferenceId;
        public String initPoint;
    }

    @Data
    public static class PaymentStatusResponse {
        public String paymentId;
        public String status;
        public String detail;
    }
}
