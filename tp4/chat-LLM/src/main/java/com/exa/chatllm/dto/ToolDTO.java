package com.exa.chatllm.dto;

import lombok.Value;

@Value
public class ToolDTO {
    String type = "function";
    FunctionDTO function;
}
