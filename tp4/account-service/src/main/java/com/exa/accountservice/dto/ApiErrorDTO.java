package com.exa.accountservice.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class ApiErrorDTO implements Serializable {
    private String error;
    private String message;
}
