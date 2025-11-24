package com.exa.scooterservice.repository;

import com.exa.scooterservice.model.Scooter;
import com.exa.scooterservice.model.ScooterState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScooterRepository extends JpaRepository<Scooter, Integer> {
    List<Scooter> findByState(ScooterState state);
}
