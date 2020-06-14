package com.leverx.blog.service.impl;

import com.leverx.blog.service.EmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender sender;
    private final SimpleMailMessage email;

    public EmailServiceImpl(JavaMailSender sender, SimpleMailMessage email) {
        this.sender = sender;
        this.email = email;
    }

    @Override
    public void sendConfirmationEmail(String to, String subject, String text) {
        email.setTo(to);
        email.setSubject(subject);
        email.setText(text);
        sender.send(email);
    }
}
