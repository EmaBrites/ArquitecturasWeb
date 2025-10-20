package com.example.tp3.controller;

import com.example.tp3.dto.EstudianteDTO;
import com.example.tp3.service.EstudianteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/estudiante")
public class EstudianteController {

    private final EstudianteService estudianteService;

    public EstudianteController(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    @GetMapping
    public List<EstudianteDTO> findAll() {
        return estudianteService.findAll();
    }

    @GetMapping("/numero-libreta/{numeroLibreta}")
    public EstudianteDTO findByNumeroLibreta(@PathVariable int numeroLibreta) {
        return estudianteService.findByNumeroLibreta(numeroLibreta);
    }

    //Dar de alta un estudiante
    @PostMapping
    public EstudianteDTO crearEstudiante(@RequestBody EstudianteDTO dto) {
        return estudianteService.crearEstudiante(dto);
    }

    // Recuperar todos los estudiantes ordenados por apellido
    @GetMapping("/ordenados")
    public List<EstudianteDTO> findAllOrdenadosPorApellido() {
        return estudianteService.findAllOrdenadosPorApellido();
    }

    @GetMapping("/filtrados")
    public List<EstudianteDTO> findByCarreraYCiudad(@RequestParam String carrera, @RequestParam String ciudad) {
        return estudianteService.findByCarreraYCiudad(carrera, ciudad);
    }

    @GetMapping("/genero/{genero}")
    public List<EstudianteDTO> findByGenero(@PathVariable String genero) {
        return estudianteService.findByGenero(genero);
    }
}
