package com.sliit.Notification_Service.model;

import lombok.Getter;

@Getter
public class CustomerPendingRegistration {
    // Getters and Setters
    private String username;
    private String fullName;
    private String phone;
    private String email;
    private String password;
    private int otp;
    private long expiryTime;

    public void setUsername(String username) { this.username = username; }

    public void setFullName(String fullName) { this.fullName = fullName; }

    public void setPhone(String phone) { this.phone = phone; }

    public void setEmail(String email) { this.email = email; }

    public void setPassword(String password) { this.password = password; }

    public void setOtp(int otp) { this.otp = otp; }

    public void setExpiryTime(long expiryTime) { this.expiryTime = expiryTime; }
}
