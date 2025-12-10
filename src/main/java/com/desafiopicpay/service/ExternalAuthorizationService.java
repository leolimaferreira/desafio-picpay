package com.desafiopicpay.service;

import com.desafiopicpay.dto.externalauthorization.AuthorizationResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExternalAuthorizationService {

    private final RestTemplate restTemplate;
    private static final String AUTHORIZATION_URL = "https://util.devi.tools/api/v2/authorize";

    public boolean authorize() {
        try {
            var response = restTemplate.getForObject(AUTHORIZATION_URL, AuthorizationResponseDTO.class);
            return response != null && "success".equals(response.status());
        } catch (Exception e) {
            log.error("Error while consulting authorization service: {}", e.getMessage());
            return false;
        }
    }
}
