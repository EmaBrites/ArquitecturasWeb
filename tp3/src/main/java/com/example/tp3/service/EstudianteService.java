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

    @Transactional(readOnly = true)
    public List<EstudianteDTO> findAll() {
        List<Estudiante> estudiantes = estudianteRepository.findAll();
        return estudiantes.stream().map(
                e -> EstudianteDTO.builder()
                        .nombre(e.getNombre())
                        .apellido(e.getApellido())
                        .edad(e.getEdad())
                        .genero(e.getGenero())
                        .numeroLibreta(e.getNumeroLibreta())
                        .ciudad(e.getCiudad())
                        .dni(e.getDni())
                        .build()).toList();
    }

    @Transactional
    public EstudianteDTO crearEstudiante(EstudianteDTO dto) {
        Estudiante estudianteReq = Estudiante.builder()
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .edad(dto.getEdad())
                .genero(dto.getGenero())
                .numeroLibreta(dto.getNumeroLibreta())
                .ciudad(dto.getCiudad())
                .dni(dto.getDni())
                .build();
        Estudiante estudiante = estudianteRepository.save(estudianteReq);
        return EstudianteDTO.builder()
                .nombre(estudiante.getNombre())
                .apellido(estudiante.getApellido())
                .edad(estudiante.getEdad())
                .genero(estudiante.getGenero())
                .numeroLibreta(estudiante.getNumeroLibreta())
                .ciudad(estudiante.getCiudad())
                .dni(estudiante.getDni())
                .build();
    }

    @Transactional(readOnly = true)
    public List<EstudianteDTO> findAllOrdenadosPorApellido() {
        List<Estudiante> estudiantes = estudianteRepository.findAll(Sort.by("apellido"));
        return estudiantes.stream().map(e -> EstudianteDTO.builder()
                .nombre(e.getNombre())
                .apellido(e.getApellido())
                .edad(e.getEdad())
                .genero(e.getGenero())
                .numeroLibreta(e.getNumeroLibreta())
                .ciudad(e.getCiudad())
                .dni(e.getDni())
                .build()).toList();
    }

    @Transactional(readOnly = true)
    public EstudianteDTO findByNumeroLibreta(int numeroLibreta) {
        Estudiante estudiante = estudianteRepository.findByLibretaUni(numeroLibreta);
        return EstudianteDTO.builder()
                .nombre(estudiante.getNombre())
                .apellido(estudiante.getApellido())
                .edad(estudiante.getEdad())
                .genero(estudiante.getGenero())
                .numeroLibreta(estudiante.getNumeroLibreta())
                .ciudad(estudiante.getCiudad())
                .dni(estudiante.getDni())
                .build();
    }

    @Transactional(readOnly = true)
    public List<EstudianteDTO> findByCarreraYCiudad(String carrera, String ciudad) {
        List<Estudiante> estudiantes = estudianteRepository.findByCarreraYCiudad(carrera, ciudad);
        return estudiantes
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private EstudianteDTO convertToDto(Estudiante estudiante) {
        return new EstudianteDTO(estudiante.getNombre(), estudiante.getApellido(), estudiante.getEdad(), estudiante.getGenero(), estudiante.getNumeroLibreta(), estudiante.getCiudad(), estudiante.getDni());
    }

    public List<EstudianteDTO> findByGenero(String genero) {
        List<Estudiante> estudiantes = estudianteRepository.findByGenero(genero);
        return estudiantes.stream().map(e -> EstudianteDTO.builder()
                .nombre(e.getNombre())
                .apellido(e.getApellido())
                .edad(e.getEdad())
                .genero(e.getGenero())
                .numeroLibreta(e.getNumeroLibreta())
                .ciudad(e.getCiudad())
                .dni(e.getDni())
                .build()).toList();
    }
}
