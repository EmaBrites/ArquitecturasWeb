package com.example.tp3.service;

import com.example.tp3.dto.CarreraInscriptosDTO;
import com.example.tp3.dto.ReporteCarreraDTO;
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

    public List<ReporteCarreraDTO> reporteCarrera() {
        return carreraRepository.reporteCarreraRaw().stream()
                .map(row -> new ReporteCarreraDTO(
                        (String) row[1],               // nombreCarrera
                        ((Number) row[2]).intValue(),  // año
                        ((Number) row[3]).longValue(), // inscriptos
                        ((Number) row[4]).longValue()  // egresados
                ))
                .toList();
    }
}
