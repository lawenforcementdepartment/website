package com.er.led.service;

import com.er.led.model.Individual;
import com.er.led.repository.IndividualRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final IndividualRepository individualRepository;

    public UserDetailsServiceImpl(IndividualRepository individualRepository) {
        this.individualRepository = individualRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Individual> individualOptional = individualRepository.findByUsername(username);
        if (individualOptional.isEmpty()) {
            throw new UsernameNotFoundException("Username %s does not exist".formatted(username));
        }
        Individual individual = individualOptional.get();

        return new User(individual.getUsername(), individual.getPasswordHash(), getAuthorities(individual));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Individual individual) {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + individual.getRank().name()));
    }
}