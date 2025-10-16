package com.example.tp3.controller;

import com.example.tp3.dto.EstudianteCarreraDTO;
import com.example.tp3.service.EstudianteCarreraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estudianteCarrera")
public class EstudianteCarreraController {

    @Autowired
    private EstudianteCarreraService estudianteCarreraService;
    @GetMapping("")
    public List<EstudianteCarreraDTO> getAll() {
        return estudianteCarreraService.getAll();
    }

    @PostMapping("/inscribir")
    public EstudianteCarreraDTO inscribir(@RequestBody EstudianteCarreraDTO dto) {
        return estudianteCarreraService.inscribirEnCarrera(dto);
    }
}
