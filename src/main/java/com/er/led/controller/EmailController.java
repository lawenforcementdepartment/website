package com.er.led.controller;

import com.er.led.exception.EmailException;
import com.er.led.model.Individual;
import com.er.led.repository.IndividualRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EmailController {
    private final IndividualRepository repository;

    public EmailController(IndividualRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/verify")
    public String verify(@RequestParam String token, Model model) {

        Individual individual = repository.findByVerificationToken(token)
                .orElseThrow(() -> new EmailException("Invalid token"));

        individual.setEmailVerified(true);
        individual.setVerificationToken(null);

        repository.save(individual);

        model.addAttribute("message", "Account verified. You can now log in.");
        return "pages/login";
    }
}
