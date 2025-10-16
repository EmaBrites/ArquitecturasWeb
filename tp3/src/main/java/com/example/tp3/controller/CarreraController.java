package com.example.tp3.controller;

import com.example.tp3.repository.CarreraRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carreras")
public class CarreraController {

    private final CarreraRepository repository;

    public CarreraController(CarreraRepository repository) {
        this.repository = repository;
    }

}