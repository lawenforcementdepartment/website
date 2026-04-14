package com.er.led.service;

import com.er.led.model.Individual;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Value("email.domain")
    private String domain;

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendVerificationEmail(Individual individual) {
        String verifyQueryString = "verify?token=";
        String link = domain + verifyQueryString + individual.getVerificationToken();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(individual.getEmail());
        message.setSubject("Verify your account");
        message.setText("Click to verify your account:\n" + link);

        mailSender.send(message);
    }
}
