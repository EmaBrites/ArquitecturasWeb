package com.exa.scooterservice.repository;

import com.exa.scooterservice.model.Scooter;
import com.exa.scooterservice.model.ScooterState;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScooterRepository extends MongoRepository<Scooter, String> {
    List<Scooter> findByState(ScooterState state);
}
