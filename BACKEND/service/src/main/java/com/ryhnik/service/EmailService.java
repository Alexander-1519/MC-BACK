package com.ryhnik.service;

import com.ryhnik.entity.User;
import com.ryhnik.exception.Code;
import com.ryhnik.exception.EntityNotFoundException;
import com.ryhnik.repository.UserRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class EmailService {

    private static final String SUBJECT = "registration";
    private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;

    public EmailService(JavaMailSender javaMailSender,
                        UserRepository userRepository) {
        this.javaMailSender = javaMailSender;
        this.userRepository = userRepository;
    }

    public void sendApproveLink(String  email){
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);
        message.setSubject(SUBJECT);

        String url = "localhost:8080/api/v1/approve-account?email=" + email;
        String approvalLink = "<a href=\"" + url + "\">approve account</a>";
        message.setText(approvalLink);

        javaMailSender.send(message);
    }

    @Async
    public void sendApproveLinkByUser(Principal principal){
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new EntityNotFoundException(Code.UNEXPECTED));
        String email = user.getEmail();

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);
        message.setSubject(SUBJECT);

        String url = "localhost:8080/api/v1/approve-account?email=" + email;
        String approvalLink = "<a href=\"" + url + "\">approve account</a>";
        message.setText(approvalLink);

        javaMailSender.send(message);
    }
}
