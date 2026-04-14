package com.er.led.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
public class Individual implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Length(min = 2, max = 42)
    @Column(unique = true, nullable = false)
    private String username;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private boolean emailVerified = false;

    @Column(unique = true)
    private String verificationToken;

    @Column(nullable = false)
    @JsonIgnore
    private String passwordHash;

    @Valid
    private Faction faction;

    @Valid
    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private Rank rank = Rank.Civilian;

    private LocalDateTime lastLogin;
}
