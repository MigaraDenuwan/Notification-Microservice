package com.sliit.Notification_Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    private final JavaMailSender mailSender;

    public EmailController(JavaMailSender mailSender){
        this.mailSender = mailSender;
    }

    @RequestMapping("/send-email")
    public String sendEmail(){
        try {
            SimpleMailMessage massage = new SimpleMailMessage();

            massage.setFrom("it22143204@my.sliit.lk");
            massage.setTo("it22143204@my.sliit.lk");
            massage.setSubject("Simple text email");
            massage.setText("aaaaaaaaaaa");

            mailSender.send(massage);
            return "Success!";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
