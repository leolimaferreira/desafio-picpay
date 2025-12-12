package com.desafiopicpay.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
public class MockNotificationService {

    private final Random random = new Random();

    @Async
    public void sendNotification(String userId, String message) {
        try {
            Thread.sleep(random.nextInt(1000));

            boolean success = random.nextInt(100) < 95;

            if (success) {
                log.info("Mock Notification sent to user {}: {}", userId, message);
            } else {
                log.error("Mock Notification failed for user {}", userId);
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Mock Notification interrupted for user {}", userId);
        }
    }
}