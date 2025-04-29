package com.sliit.Notification_Service;

import com.sliit.Notification_Service.service.OtpService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/notification")
public class EmailController {

    private final JavaMailSender mailSender;
    private final OtpService otpService;

    public EmailController(JavaMailSender mailSender, OtpService otpService) {
        this.mailSender = mailSender;
        this.otpService = otpService;
    }

    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtpEmail(@RequestBody com.sliit.Notification_Service.model.CustomerPendingRegistration formData) {
        try {
            int otp = (int) (Math.random() * 900000) + 100000;
            formData.setOtp(otp);
            formData.setExpiryTime(System.currentTimeMillis() + 5 * 60 * 1000); // 5 min
            otpService.storePending(formData.getEmail(), formData);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(formData.getEmail());
            helper.setSubject("Snap Eats - Verify your email");

            try (var inputStream = getClass().getClassLoader().getResourceAsStream("templates/registerVerifyMail.html")) {
                if (inputStream == null) {
                    throw new RuntimeException("Template not found");
                }
                String html = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                html = html.replace("[OTP]", String.valueOf(otp));
                helper.setText(html, true);
            }


            mailSender.send(message);
            return ResponseEntity.ok("OTP sent");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to send OTP: " + e.getMessage());
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody Map<String, Object> payload) {
        try {
            String email = (String) payload.get("email");
            int otp = Integer.parseInt(payload.get("otp").toString());

            com.sliit.Notification_Service.model.CustomerPendingRegistration pending = otpService.getPending(email);

            if (pending == null)
                return ResponseEntity.badRequest().body("No pending registration");

            if (System.currentTimeMillis() > pending.getExpiryTime())
                return ResponseEntity.badRequest().body("OTP expired");

            if (pending.getOtp() != otp)
                return ResponseEntity.badRequest().body("Invalid OTP");

            otpService.removePending(email);
            return ResponseEntity.ok("Registration successful!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Verification failed: " + e.getMessage());
        }
    }


    // Restaurant Registration Success Email
    @GetMapping("/send-restaurant-registration")
    public String sendRestaurantRegistrationEmail(@RequestParam String toEmail) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("it22143204@my.sliit.lk");
            helper.setTo(toEmail);
            helper.setSubject("Restaurant Registration Success - Snap Eats");

            // Load HTML template for restaurant registration success email
            try (var inputStream = Objects.requireNonNull(
                    EmailController.class.getResourceAsStream("/templates/restaurantRegisteredMail.html"))) {
                String html = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                helper.setText(html, true);
            }

            mailSender.send(message);
            return "Restaurant registration email sent successfully to: " + toEmail;
        } catch (Exception e) {
            return "Failed to send restaurant registration email: " + e.getMessage();
        }
    }

    // Driver Registration Success Email
    @GetMapping("/send-driver-registration")
    public String sendDriverRegistrationEmail(@RequestParam String toEmail) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("it22143204@my.sliit.lk");
            helper.setTo(toEmail);
            helper.setSubject("Driver Registration Success - Snap Eats");

            // Load HTML template for driver registration success email
            try (var inputStream = Objects.requireNonNull(
                    EmailController.class.getResourceAsStream("/templates/driverRegisteredMail.html"))) {
                String html = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                helper.setText(html, true);
            }

            mailSender.send(message);
            return "Driver registration email sent successfully to: " + toEmail;
        } catch (Exception e) {
            return "Failed to send driver registration email: " + e.getMessage();
        }
    }
}
