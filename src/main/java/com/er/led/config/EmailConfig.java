package com.er.led.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfig {

    @Value("${spring.mail.username}")
    private String smtpUsername;
    @Value("${spring.mail.password}")
    private String smtpPassword;
    @Value("${spring.mail.host}")
    private String smtpHost;
    @Value("${spring.mail.port}")
    private String smtpPort;

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(smtpHost);
        System.out.println(smtpPort);
        mailSender.setPort(Integer.parseInt(smtpPort));

        mailSender.setUsername(smtpUsername);
        mailSender.setPassword(smtpPassword);

        return mailSender;
    }
}
