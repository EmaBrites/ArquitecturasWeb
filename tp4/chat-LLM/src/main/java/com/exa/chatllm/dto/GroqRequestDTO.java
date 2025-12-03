package com.exa.chatllm.dto;

import java.util.List;

public record GroqRequestDTO(
        String model,
        List<MessageDTO> messages,
        List<ToolDTO> tools) {
}
