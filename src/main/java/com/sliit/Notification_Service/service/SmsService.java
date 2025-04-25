package com.sliit.Notification_Service.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class SmsService {

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.phone.number}")
    private String fromPhoneNumber;

    @PostConstruct
    public void init() {
        Twilio.init(accountSid, authToken);
    }

    public String sendSms(String toPhoneNumber, String messageText) {
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber(toPhoneNumber),
                new com.twilio.type.PhoneNumber(fromPhoneNumber),
                messageText
        ).create();

        return message.getSid();
    }

    // Custom methods for order status notifications
    public String sendOrderDeliveredNotification(String toPhoneNumber) {
        String messageText = "Your order has been delivered successfully! Enjoy your meal!";
        return sendSms(toPhoneNumber, messageText);
    }

    public String sendOrderOnTheWayNotification(String toPhoneNumber) {
        String messageText = "Your order is on the way and will arrive at your location shortly!";
        return sendSms(toPhoneNumber, messageText);
    }
}
