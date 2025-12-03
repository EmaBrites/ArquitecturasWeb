package com.exa.chatllm.dto;

public record ChatRequestDTO(
        Integer accountId,
        String prompt
) {
}
