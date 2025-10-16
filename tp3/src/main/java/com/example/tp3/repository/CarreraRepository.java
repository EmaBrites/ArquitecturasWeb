package com.example.tp3.repository;

import com.example.tp3.dto.CarreraInscriptosDTO;
import com.example.tp3.model.Carrera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarreraRepository extends JpaRepository<Carrera, Integer> {

    @Query("SELECT new com.example.tp3.dto.CarreraInscriptosDTO(c.nombre, COUNT(ec.estudiante)) " +
            "FROM EstudianteCarrera ec " +
            "JOIN ec.carrera c " +
            "GROUP BY c.nombre " +
            "ORDER BY COUNT(ec.estudiante) DESC")
    List<CarreraInscriptosDTO> findCarrerasOrderByInscriptosDesc();
}
