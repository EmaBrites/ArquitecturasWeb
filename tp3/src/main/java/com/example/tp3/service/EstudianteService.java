package com.example.tp3.service;

import com.example.tp3.dto.EstudianteDTO;
import com.example.tp3.model.Estudiante;
import com.example.tp3.repository.EstudianteRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional(readOnly = true)
    public List<EstudianteDTO> findByCarreraYCiudad(String carrera, String ciudad) {
        List<Estudiante> estudiantes = estudianteRepository.findByCarreraYCiudad(carrera, ciudad);
        return estudiantes
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private EstudianteDTO convertToDto(Estudiante estudiante){
        return new EstudianteDTO(estudiante.getNombre(), estudiante.getApellido(),estudiante.getEdad(), estudiante.getGenero(), estudiante.getNumeroLibreta(), estudiante.getCiudad(), estudiante.getDni());
    }
}
