package com.example.tp3.repository;

import com.example.tp3.dto.CarreraInscriptosDTO;
import com.example.tp3.model.Carrera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarreraRepository extends JpaRepository<Carrera, Integer> {

    @Query("SELECT new com.example.tp3.dto.CarreraInscriptosDTO(c.nombre, COUNT(ec.estudiante)) " +
            "FROM EstudianteCarrera ec " +
            "JOIN ec.carrera c " +
            "GROUP BY c.nombre " +
            "ORDER BY COUNT(ec.estudiante) DESC")
    List<CarreraInscriptosDTO> findCarrerasOrderByInscriptosDesc();

    @Query(value = """
    SELECT 
        c.id AS carreraId,
        c.nombre AS nombreCarrera,
        t.anio AS anio,
        SUM(CASE WHEN t.tipo = 'INSCRIPCION' THEN 1 ELSE 0 END) AS inscriptos,
        SUM(CASE WHEN t.tipo = 'EGRESO' THEN 1 ELSE 0 END) AS egresados
    FROM (
        SELECT ec.id_carrera AS idCarrera, ec.inscripcion AS anio, 'INSCRIPCION' AS tipo 
        FROM estudiante_carrera ec
        UNION ALL
        SELECT ec.id_carrera AS idCarrera, ec.graduacion AS anio, 'EGRESO' AS tipo 
        FROM estudiante_carrera ec 
        WHERE ec.graduacion IS NOT NULL AND ec.graduacion > 0
    ) t
    JOIN carrera c ON t.idCarrera = c.id
    GROUP BY c.id, c.nombre, t.anio
    ORDER BY c.nombre ASC, t.anio ASC
""", nativeQuery = true)
    List<Object[]> reporteCarreraRaw();

}
