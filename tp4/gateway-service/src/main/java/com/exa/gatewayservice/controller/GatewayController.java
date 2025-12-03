package com.exa.gatewayservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class GatewayController {
    @Autowired
    private RestTemplate restTemplate;


    @PostMapping(value = "accounts/**")
    public ResponseEntity<String> postRedirectMap(HttpServletRequest request, @RequestBody(required = false) String body) {
        String url = "http://localhost:8001" + request.getRequestURI();

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        return restTemplate.postForEntity(url, entity, String.class);
    }

    @GetMapping(value = "accounts/**")
    public ResponseEntity<String> redirectMap(HttpServletRequest request) {
        String url = "http://localhost:8001" + request.getRequestURI();

        return restTemplate.getForEntity(url, String.class);
    }

    @PutMapping(value = "accounts/**")
    public ResponseEntity<String> putRedirectMap(HttpServletRequest request, @RequestBody(required = false) String body) {
        String url = "http://localhost:8001" + request.getRequestURI();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        return restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
    }

    @DeleteMapping(value = "accounts/**")
    public ResponseEntity<String> deleteRedirectMap(HttpServletRequest request) {
        String url = "http://localhost:8001" + request.getRequestURI();

        return restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
    }
}
