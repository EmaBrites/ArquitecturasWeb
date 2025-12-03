package com.exa.chatllm.client;

import com.exa.chatllm.client.config.GroqApiKeyConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "groqClient", url = "${groq.api.url}", configuration = GroqApiKeyConfig.class)
public interface GroqClient {
    @PostMapping("/v1/chat/completions")
    String getChatCompletion(String requestBody);
}
