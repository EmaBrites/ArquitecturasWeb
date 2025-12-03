package com.exa.gatewayservice.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder){
        return builder
                .interceptors(new RequestResponseLoggingInterceptor())
                .build();
    }

    public class RequestResponseLoggingInterceptor implements ClientHttpRequestInterceptor {

        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                            ClientHttpRequestExecution execution) throws IOException {
            System.out.println("Request: " + request.getMethod() + " " + request.getURI());
            System.out.println("Request Body: " + new String(body, StandardCharsets.UTF_8));
            ClientHttpResponse response = execution.execute(request, body);
            System.out.println("Response: " + response.getStatusCode());
            return response;
        }
    }
}
