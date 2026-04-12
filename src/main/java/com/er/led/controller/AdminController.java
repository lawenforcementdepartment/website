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

import java.util.Optional;

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
    public void idiotNet(@RequestParam Long userId) {
        Individual individual = individualRepository.findById(userId).orElseThrow();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null) {
            throw new IdiotException("No auth context, how?");
        }

        String currentUsername = auth.getName();
        if (individual.getUsername().equals(currentUsername)) {
            throw new IdiotException("Can't do that to yourself");
        }
    }

    @PostMapping("/promote")
    public String promote(@RequestParam Long userId) {
        Individual individual = individualRepository.findById(userId).orElseThrow();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null) {
            throw new IdiotException("No auth context, how?");
        }

        Optional<Individual> callingUser = individualRepository.findByUsername(auth.getName());

        if (callingUser.isEmpty() || callingUser.get().getRank().ordinal() >= individual.getRank().ordinal()) {
            throw new IdiotException("Can't promote above own rank");
        }

        individual.setRank(Rank.values()[individual.getRank().ordinal() - 1]);
        individualRepository.save(individual);
        return "redirect:/admin";
    }

    @PostMapping("/demote")
    public String demote(@RequestParam Long userId) {
        Individual individual = individualRepository.findById(userId).orElseThrow();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null) {
            throw new IdiotException("No auth context, how?");
        }

        Optional<Individual> callingUser = individualRepository.findByUsername(auth.getName());

        if (callingUser.isEmpty() || callingUser.get().getRank().ordinal() >= individual.getRank().ordinal()) {
            throw new IdiotException("Can't demote users of equal or higher rank");
        }

        if (individual.getRank().ordinal() >= Rank.values().length - 1) {
            throw new IdiotException("Can't exceed " + individual.getRank());
        }

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