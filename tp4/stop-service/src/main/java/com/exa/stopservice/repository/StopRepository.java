package com.exa.stopservice.repository;

import com.exa.stopservice.entity.Stop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StopRepository extends JpaRepository<Stop,Integer> {
}
