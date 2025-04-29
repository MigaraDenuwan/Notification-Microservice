package com.sliit.Notification_Service.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OtpService {
    private final Map<String, com.sliit.Notification_Service.model.CustomerPendingRegistration> pendingMap = new ConcurrentHashMap<>();

    public void storePending(String email, com.sliit.Notification_Service.model.CustomerPendingRegistration data) {
        pendingMap.put(email, data);
    }

    public com.sliit.Notification_Service.model.CustomerPendingRegistration getPending(String email) {
        return pendingMap.get(email);
    }

    public void removePending(String email) {
        pendingMap.remove(email);
    }
}
