package com.exa.chatllm.client.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GroqApiKeyConfig {
    @Value("${groq.api.key}")
    private String apiKey;

    @Bean
    public RequestInterceptor apiKeyRequestInterceptor() {
        return requestTemplate -> requestTemplate.header("Authorization", "Bearer " + apiKey);
    }
}
