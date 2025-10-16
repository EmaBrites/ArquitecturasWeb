package com.example.tp3.controller;

import com.example.tp3.dto.CarreraInscriptosDTO;
import com.example.tp3.repository.CarreraRepository;
import com.example.tp3.service.CarreraService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carreras")
public class CarreraController {

    private final CarreraService carreraService;

    public CarreraController(CarreraService carreraService) {
        this.carreraService = carreraService;
    }

    // Recuperar las carreras con estudiantes inscriptos, ordenadas por cantidad
    @GetMapping("/inscriptos")
    public List<CarreraInscriptosDTO> findCarrerasConInscriptosOrdenadas() {
        return carreraService.findCarrerasConInscriptosOrdenadas();
    }

}