package com.er.led.config;

import com.er.led.model.Individual;
import com.er.led.model.Rank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {

    private final Individual individual;

    public UserDetailsImpl(Individual individual) {
        this.individual = individual;
    }

    @Override
    public String getUsername() {
        return individual.getUsername();
    }

    @Override
    public String getPassword() {
        return individual.getPasswordHash();
    }

    @Override
    public boolean isEnabled() {
        return individual.isEmailVerified();
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + Rank.Civilian.name()));
    }
}