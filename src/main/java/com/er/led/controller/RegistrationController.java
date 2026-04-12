package com.er.led.controller;

import com.er.led.service.RegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String email, @RequestParam String password,
            @RequestParam String badge, Model model) {

        try {
            registrationService.register(username, email, password, badge);
            model.addAttribute("message", "Registration successful. Check your email.");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }

        return "pages/register";
    }
}