package com.sliit.Notification_Service.controller;

import com.sliit.Notification_Service.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sms")
public class SmsController {

    @Autowired
    private SmsService smsService;

    @PostMapping("/send-order-delivered")
    public String sendOrderDeliveredSms(@RequestParam String to) {
        return smsService.sendOrderDeliveredNotification(to);
    }

    @PostMapping("/send-order-on-the-way")
    public String sendOrderOnTheWaySms(@RequestParam String to) {
        return smsService.sendOrderOnTheWayNotification(to);
    }
}
