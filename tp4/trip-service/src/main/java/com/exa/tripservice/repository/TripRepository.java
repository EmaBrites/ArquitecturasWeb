package com.exa.tripservice.repository;

import com.exa.tripservice.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Long> {
    List<Trip> findByAccountId(Long accountId);
}
