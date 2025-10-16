package com.example.tp3.repository;

import com.example.tp3.model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Integer> {
    Optional<Estudiante> findByNumeroLibreta(int numeroLibreta);
}
