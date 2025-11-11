package com.exa.scooterservice.service;

import com.exa.scooterservice.dto.NearbyDTO;
import com.exa.scooterservice.dto.TelemetryDTO;
import com.exa.scooterservice.model.Scooter;
import com.exa.scooterservice.model.ScooterState;
import com.exa.scooterservice.repository.ScooterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScooterService {

    @Autowired
    private ScooterRepository repository;

    // CREATE
    public Scooter createScooter(Scooter scooter) {
        return repository.save(scooter);
    }

    // READ - get all
    public List<Scooter> getAllScooters() {
        return repository.findAll();
    }

    // READ - get by id
    public Optional<Scooter> getScooterById(Long id) {
        return repository.findById(id);
    }

    // UPDATE
    public Scooter updateScooter(Long id, Scooter newData) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setSerial(newData.getSerial());
                    existing.setLatitude(newData.getLatitude());
                    existing.setLongitude(newData.getLongitude());
                    existing.setState(newData.getState());
                    existing.setAccumulatedKms(newData.getAccumulatedKms());
                    existing.setAccumulatedMinutes(newData.getAccumulatedMinutes());
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Scooter not found"));
    }

    // DELETE (lógico o real)
    public void deleteScooter(Long id) {
        repository.deleteById(id);
    }

    // ADMIN CAMBIA ESTADO (mantenimiento/de baja)
    public Scooter updateState(Long id, ScooterState newState) {
        return repository.findById(id)
                .map(scooter -> {
                    scooter.setState(newState);
                    return repository.save(scooter);
                })
                .orElseThrow(() -> new RuntimeException("Scooter not found"));
    }

    //SISTEMA RECIBE INFO NUEVA DEL GPS DEL MONOPATIN
    public Scooter updateTelemetry(Long id, TelemetryDTO telemetry) {
        return repository.findById(id)
            .map(scooter -> {
                // actualizar posición
                scooter.setLatitude(telemetry.getLatitude());
                scooter.setLongitude(telemetry.getLongitude());

                // sumar acumulados (si vienen valores)
                if (telemetry.getKmDelta() != null) {
                    scooter.setAccumulatedKms(
                            scooter.getAccumulatedKms() + telemetry.getKmDelta()
                    );
                }
                if (telemetry.getMinutesDelta() != null) {
                    scooter.setAccumulatedMinutes(
                            scooter.getAccumulatedMinutes() + telemetry.getMinutesDelta()
                    );
                }

                return repository.save(scooter);
            })
            .orElseThrow(() -> new RuntimeException("Scooter not found"));
    }

    //PERMITE AL USUARIO VER MONOPATINES CERCA
    public List<NearbyDTO> findNearby(Double lat, Double lon, Double radiusKm) {
        List<Scooter> available = repository.findByState(ScooterState.AVAILABLE);

        return available.stream()
                .map(s -> {
                    double distance = distanceKm(lat, lon, s.getLatitude(), s.getLongitude());
                    return new NearbyDTO(s.getId(), s.getSerial(), s.getLatitude(), s.getLongitude(), distance);
                })
                .filter(dto -> dto.getDistanceKm() <= radiusKm)
                .sorted(Comparator.comparingDouble(NearbyDTO::getDistanceKm))
                .collect(Collectors.toList());
    }

    // --- utilitario para calcular distancia con fórmula Haversine ---
    private double distanceKm(double lat1, double lon1, double lat2, double lon2) {
        if (lat1 == 0 || lon1 == 0 || lat2 == 0 || lon2 == 0) return Double.MAX_VALUE;

        final int R = 6371; // radio Tierra en km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

}
