package com.example.tp3.service;

import com.example.tp3.dto.EstudianteDTO;
import com.example.tp3.model.Estudiante;
import com.example.tp3.repository.EstudianteRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstudianteService {

    private final EstudianteRepository estudianteRepository;

    public EstudianteService(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    @Transactional
    public List<Estudiante> findAll() {
        return estudianteRepository.findAll();
    }

    @Transactional
    public Estudiante crearEstudiante(EstudianteDTO dto) {
        return Estudiante.builder()
                .dni(dto.getDni())
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .edad(dto.getEdad())
                .genero(dto.getGenero())
                .ciudad(dto.getCiudad())
                .numeroLibreta(dto.getNumeroLibreta())
                .build();
    }

    @Transactional
    public List<Estudiante> findAllOrdenadosPorApellido() {
        return estudianteRepository.findAll(Sort.by("apellido"));
    }
}
