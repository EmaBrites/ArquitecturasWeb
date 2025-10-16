package com.example.tp3.service;

import com.example.tp3.dto.CarreraInscriptosDTO;
import com.example.tp3.repository.CarreraRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarreraService {
    private final CarreraRepository carreraRepository;

    public CarreraService(CarreraRepository carreraRepository) {
        this.carreraRepository = carreraRepository;
    }

    public List<CarreraInscriptosDTO> findCarrerasConInscriptosOrdenadas() {
        return carreraRepository.findCarrerasOrderByInscriptosDesc();
    }
}
