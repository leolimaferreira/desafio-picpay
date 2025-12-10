package com.desafiopicpay.service;

import com.desafiopicpay.dto.notification.NotificationRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final RestTemplate restTemplate;
    private static final String NOTIFICATION_URL = "https://util.devi.tools/api/v1/notify";

    @Async
    public void sendNotification(String userId, String message) {
        try {
            restTemplate.postForObject(NOTIFICATION_URL, new NotificationRequestDTO(message), Void.class);
            log.info("Notification send to user {}", userId);
        } catch (Exception e) {
            log.error("Error while sending notification to user {}: {}", userId, e.getMessage());
        }
    }
}

