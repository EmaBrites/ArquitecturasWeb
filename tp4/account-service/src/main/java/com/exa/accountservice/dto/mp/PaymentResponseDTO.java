package com.exa.accountservice.dto.mp;

import lombok.Data;

@Data
public class PaymentResponseDTO {
    private String status;     // "approved", "rejected"
    private String paymentId;  // ID mockeado
    private Double amount;
}
