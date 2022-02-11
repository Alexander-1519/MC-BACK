package com.ryhnik.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final String SUBJECT = "registration";
    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendApproveLink(String  email){
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);
        message.setSubject(SUBJECT);

        String url = "localhost:8080/api/v1/approve-account?email" + email;
        String approvalLink = "<a href=\"" + url + "\">approve account</a>";
        message.setText(approvalLink);

        javaMailSender.send(message);
    }
}
