package com.sliit.Notification_Service.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.HashMap;
import java.util.Map;

@Service
public class SmsService {

    @Value("${textlk.api.token}")
    private String apiToken;

    @Value("${textlk.sender.id}")
    private String senderId;

    private final RestTemplate restTemplate = new RestTemplate();

    private static final String TEXT_LK_SMS_API_URL = "https://app.text.lk/api/v3/sms/send";

    public String sendSms(String toPhoneNumber, String messageText) {
        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(java.util.Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(apiToken); // Use Bearer Token Authentication

        // Create request body
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("recipient", toPhoneNumber);   // New field name
        requestBody.put("sender_id", "TextLKDemo");        // New field name
        requestBody.put("type", "plain");              // Always "plain" for normal SMS
        requestBody.put("message", messageText);       // Message content

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(TEXT_LK_SMS_API_URL, requestEntity, String.class);

        return response.getBody();
    }

    public String sendOrderDeliveredNotification(String toPhoneNumber) {
        String messageText = "Your order has been delivered successfully! Enjoy your meal!";
        return sendSms(toPhoneNumber, messageText);
    }

    public String sendOrderOnTheWayNotification(String toPhoneNumber) {
        String messageText = "Your order is on the way and will arrive at your location shortly!";
        return sendSms(toPhoneNumber, messageText);
    }
}
