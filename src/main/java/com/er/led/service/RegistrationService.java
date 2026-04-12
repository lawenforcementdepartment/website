package com.er.led.service;

import com.er.led.model.Individual;
import com.er.led.repository.IndividualRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RegistrationService {

    private final IndividualRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public RegistrationService(IndividualRepository repository,
                               PasswordEncoder passwordEncoder,
                               EmailService emailService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    public void register(String username, String email, String password, String badge) {

        if (repository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        Individual user = new Individual();
        user.setUsername(username);
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(password));
        user.setVerificationToken(UUID.randomUUID().toString());
        user.setEnabled(false);

        repository.save(user);

        emailService.sendVerificationEmail(user);
    }
}