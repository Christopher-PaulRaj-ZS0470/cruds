package com.musicplayer.auth.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiCallerService {

    private final RestTemplate restTemplate;

    public ApiCallerService() {
        this.restTemplate = new RestTemplate();
    }

    public String callConfigApiWithJwt(String jwtToken, String variable) {
        // URL of your config API
        String url = "http://localhost:8080/configProperties/get/properties/" + variable;

        // Set the Authorization header
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken); // automatically sets "Authorization: Bearer <token>"
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Make GET request
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        return response.getBody();
    }
}
