package com.exa.gatewayservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class OpenApiAggregator {

    private final WebClient webClient = WebClient.builder().build();

    @Bean
    public GroupedOpenApi authServiceApi() {
        return GroupedOpenApi.builder()
                .group("auth-service")
                .pathsToMatch("/auth/**")
                .addOpenApiCustomizer(remoteServiceCustomizer("http://localhost:8080/v3/api-docs"))
                .build();
    }

    @Bean
    public GroupedOpenApi accountServiceApi() {
        return GroupedOpenApi.builder()
                .group("account-service")
                .pathsToMatch("/accounts/**")
                .addOpenApiCustomizer(remoteServiceCustomizer("http://localhost:8001/v3/api-docs"))
                .build();
    }

    @Bean
    public GroupedOpenApi reportServiceApi() {
        return GroupedOpenApi.builder()
                .group("report-service")
                .pathsToMatch("/admin/reports/**") //TODO esto debería ser /reports solo, admin es rol
                .addOpenApiCustomizer(remoteServiceCustomizer("http://localhost:8085/v3/api-docs"))
                .build();
    }

    @Bean
    public GroupedOpenApi scooterServiceApi() {
        return GroupedOpenApi.builder()
                .group("scooter-service")
                .pathsToMatch("/scooters/**")
                .addOpenApiCustomizer(remoteServiceCustomizer("http://localhost:8081/v3/api-docs"))
                .build();
    }

    @Bean
    public GroupedOpenApi stopServiceApi() {
        return GroupedOpenApi.builder()
                .group("stop-service")
                .pathsToMatch("/stops/**")
                .addOpenApiCustomizer(remoteServiceCustomizer("http://localhost:8086/v3/api-docs"))
                .build();
    }

    @Bean
    public GroupedOpenApi tripServiceApi() {
        return GroupedOpenApi.builder()
                .group("trip-service")
                .pathsToMatch("/trips/**")
                .addOpenApiCustomizer(remoteServiceCustomizer("http://localhost:8084/v3/api-docs"))
                .build();
    }

    @Bean
    public GroupedOpenApi userServiceApi() {
        return GroupedOpenApi.builder()
                .group("user-service")
                .pathsToMatch("/users/**")
                .addOpenApiCustomizer(remoteServiceCustomizer("http://localhost:8002/v3/api-docs"))
                .build();
    }



    private OpenApiCustomizer remoteServiceCustomizer(String url) {
        return openApi -> {
            OpenAPI remoteApi = webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(OpenAPI.class)
                    .block();

            if (remoteApi != null && remoteApi.getPaths() != null) {
                openApi.getPaths().putAll(remoteApi.getPaths());
            }

            if (remoteApi != null && remoteApi.getComponents() != null) {
                if (openApi.getComponents().getSchemas() == null) {
                    openApi.getComponents().setSchemas(remoteApi.getComponents().getSchemas());
                } else {
                    openApi.getComponents().getSchemas().putAll(remoteApi.getComponents().getSchemas());
                }
            }
        };
    }
}