package com.example.tp3.repository;

import com.example.tp3.dto.EstudianteDTO;
import com.example.tp3.model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Integer> {

    @Query("SELECT DISTINCT e " +
            "FROM Estudiante e " +
                    "INNER JOIN EstudianteCarrera ec ON ec.estudiante.dni = e.dni " +
                    "INNER JOIN Carrera c ON c.id = ec.carrera.id " +
                    "WHERE e.ciudad = :ciudad " +
                    "AND c.nombre = :carrera")
    List<Estudiante> findByCarreraYCiudad(String carrera, String ciudad);

    @Query(value = "SELECT e FROM Estudiante e WHERE e.numeroLibreta = :nroLibreta")
    Estudiante findByLibretaUni(int nroLibreta);

    @Query(value = "SELECT e FROM Estudiante e WHERE e.genero = :genero")
    List<Estudiante> findByGenero(String genero);
}
