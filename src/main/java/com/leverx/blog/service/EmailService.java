package com.leverx.blog.service;

public interface EmailService {
    void sendConfirmationEmail(String to, String subject, String text);
}
