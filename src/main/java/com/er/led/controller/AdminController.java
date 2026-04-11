package com.er.led.controller;

import com.er.led.exception.IdiotException;
import com.er.led.model.Individual;
import com.er.led.model.Rank;
import com.er.led.repository.IndividualRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final IndividualRepository individualRepository;

    public AdminController(IndividualRepository individualRepository) {
        this.individualRepository = individualRepository;
    }

    @GetMapping
    public String adminPanel(Model model) {
        model.addAttribute("users", individualRepository.findAll());
        return "pages/admin";
    }

    @PostMapping("/**")
    public void idiotNet(@RequestParam Long targetUserId) {
        Individual individual = individualRepository.findById(targetUserId).orElseThrow();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assert auth != null;
        String currentUsername = auth.getName();

        if (individual.getUsername().equals(currentUsername)) {
            throw new IdiotException("Can't do that to yourself");
        }
    }

    @PostMapping("/promote")
    public String promote(@RequestParam Long targetUserId) {
        Individual individual = individualRepository.findById(targetUserId).orElseThrow();
        individual.setRank(Rank.values()[individual.getRank().ordinal() - 1]);
        individualRepository.save(individual);
        return "redirect:/admin";
    }

    @PostMapping("/demote")
    public String demote(@RequestParam Long userId) {
        Individual individual = individualRepository.findById(userId).orElseThrow();
        individual.setRank(Rank.values()[individual.getRank().ordinal() + 1]);
        individualRepository.save(individual);
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long userId) {
        Individual individual = individualRepository.findById(userId).orElseThrow();
        individualRepository.deleteById(userId);
        return "redirect:/admin";
    }
}