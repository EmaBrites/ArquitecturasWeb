package com.exa.tripservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ExternalServicesClient {

    private final RestTemplate restTemplate;

    @Value("${microservices.stop}")
    private String stopMsUrl;

    @Value("${microservices.scooter}")
    private String scooterMsUrl;

    @Value("${microservices.account}")
    private String accountMsUrl;

    //  Validar parada
    public boolean isStopValid(Double lat, Double lon) {
        String url = stopMsUrl + "/stops/validate?lat=" + lat + "&lon=" + lon;
        return restTemplate.getForObject(url, Boolean.class);
    }

    //  Cambiar scooter a disponible
    public void setScooterAvailable(Long scooterId) {
        String url = scooterMsUrl + "/scooters/" + scooterId + "/status?newStatus=AVAILABLE";
        restTemplate.put(url, null);
    }

    //  Descontar crédito
    public void chargeAccount(Long accountId, double amount) {
        String url = accountMsUrl + "/accounts/" + accountId + "/charge?amount=" + amount;
        restTemplate.put(url, null);
    }
}
