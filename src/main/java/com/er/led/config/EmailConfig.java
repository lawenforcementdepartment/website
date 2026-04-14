package com.er.led.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfig {

    @Value("SMTP_USERNAME")
    private String smtpUsername;
    @Value("SMTP_PASSWORD")
    private String smtpPassword;
    @Value("SMTP_HOST")
    private String smtpHost;
    @Value("SMTP_PORT")
    private String smtpPort;

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(smtpHost);
        mailSender.setPort(Integer.parseInt(smtpPort));

        mailSender.setUsername(smtpUsername);
        mailSender.setPassword(smtpPassword);

        return mailSender;
    }
}
