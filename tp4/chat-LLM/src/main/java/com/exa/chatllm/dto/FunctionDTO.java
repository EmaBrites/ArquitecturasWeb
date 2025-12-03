package com.exa.chatllm.dto;

import java.io.Serializable;
import java.util.Map;

public record FunctionDTO(
        String name,
        String description,
        Map<String, Object> parameters) implements Serializable {
}
