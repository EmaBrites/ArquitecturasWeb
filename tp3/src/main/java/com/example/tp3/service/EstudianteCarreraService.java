package com.example.tp3.service;

import com.example.tp3.dto.EstudianteCarreraDTO;
import com.example.tp3.model.Carrera;
import com.example.tp3.model.Estudiante;
import com.example.tp3.model.EstudianteCarrera;
import com.example.tp3.repository.CarreraRepository;
import com.example.tp3.repository.EstudianteCarreraRepository;
import com.example.tp3.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstudianteCarreraService {

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private CarreraRepository carreraRepository;

    @Autowired
    private EstudianteCarreraRepository estudianteCarreraRepository;

    public EstudianteCarreraDTO inscribirEnCarrera(EstudianteCarreraDTO dto) {
        Estudiante estudiante = estudianteRepository.findById(dto.getIdEstudiante())
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
        Carrera carrera = carreraRepository.findById(dto.getIdCarrera())
                .orElseThrow(() -> new RuntimeException("Carrera no encontrada"));
        EstudianteCarrera nueva = EstudianteCarrera.builder()
                .inscripcion(dto.getInscripcion())
                .graduacion(dto.getGraduacion())
                .antiguedad(dto.getAntiguedad())
                .estudiante(estudiante)
                .carrera(carrera)
                .build();
        EstudianteCarrera guardado = estudianteCarreraRepository.save(nueva);
        return new EstudianteCarreraDTO(
                guardado.getId(),
                guardado.getInscripcion(),
                guardado.getGraduacion(),
                guardado.getAntiguedad(),
                carrera.getId(),
                estudiante.getDni(),
                estudiante.getNombre(),
                carrera.getNombre(),
                estudiante.getApellido(),
                estudiante.getGenero(),
                estudiante.getNumeroLibreta()
        );
    }
    public List<EstudianteCarreraDTO> getAll() {
        return estudianteCarreraRepository.findAll()
                .stream()
                .map(ec -> new EstudianteCarreraDTO(
                        ec.getId(),
                        ec.getInscripcion(),
                        ec.getGraduacion(),
                        ec.getAntiguedad(),
                        ec.getCarrera().getId(),
                        ec.getEstudiante().getDni(),
                        ec.getEstudiante().getNombre(),
                        ec.getCarrera().getNombre(),
                        ec.getEstudiante().getApellido(),
                        ec.getEstudiante().getGenero(),
                        ec.getEstudiante().getNumeroLibreta()
                ))
                .collect(Collectors.toList());
    }
}
