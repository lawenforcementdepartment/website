package com.er.led.repository;

import com.er.led.model.Individual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Repository
public interface IndividualRepository extends JpaRepository<Individual, Long> {

    Set<Individual> findByIdIn(Collection<Long> ids);

    Optional<Individual> findByUsername(String username);

    Optional<Individual> findByVerificationToken(String token);
}
