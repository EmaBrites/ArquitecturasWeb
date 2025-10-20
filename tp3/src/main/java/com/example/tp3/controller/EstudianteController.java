package com.example.tp3.controller;

import com.example.tp3.dto.EstudianteDTO;
import com.example.tp3.model.Estudiante;
import com.example.tp3.service.EstudianteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estudiante")
public class EstudianteController {

    private final EstudianteService estudianteService;

    public EstudianteController(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    @GetMapping
    public List<Estudiante> findAll() {
        return estudianteService.findAll();
    }
    //Dar de alta un estudiante
    @PostMapping
    public Estudiante crearEstudiante(@RequestBody EstudianteDTO dto) {
        return estudianteService.crearEstudiante(dto);
    }

    // Recuperar todos los estudiantes ordenados por apellido
    @GetMapping("/ordenados")
    public List<Estudiante> findAllOrdenadosPorApellido() {
        return estudianteService.findAllOrdenadosPorApellido();
    }

}
