package com.desafiopicpay.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
public class MockAuthorizationService {

    private final Random random = new Random();

    public boolean authorize() {
        boolean authorized = random.nextInt(100) < 90;

        log.info("Mock Authorization Service - Result: {}", authorized ? "AUTHORIZED" : "DENIED");

        try {
            Thread.sleep(random.nextInt(500));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return authorized;
    }
}