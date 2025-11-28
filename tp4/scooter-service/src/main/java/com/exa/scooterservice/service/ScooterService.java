package com.exa.scooterservice.service;

import com.exa.scooterservice.Exception.NotFoundException;
import com.exa.scooterservice.dto.CreateScooterDTO;
import com.exa.scooterservice.dto.NearbyDTO;
import com.exa.scooterservice.dto.ScooterDTO;
import com.exa.scooterservice.dto.TelemetryDTO;
import com.exa.scooterservice.model.Scooter;
import com.exa.scooterservice.model.ScooterState;
import com.exa.scooterservice.repository.ScooterRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ScooterService {
    private final ScooterRepository scooterRepository;

    public ScooterService(ScooterRepository scooterRepository){
        this.scooterRepository = scooterRepository;
    }

    public ScooterDTO createScooter(CreateScooterDTO scooterDTO) {
        Scooter scooter = new Scooter();
        BeanUtils.copyProperties(scooterDTO,scooter);

        Scooter newScooter = scooterRepository.save(scooter);
        return new ScooterDTO(newScooter);
    }

    public List<ScooterDTO> getAllScooters() {
        List<ScooterDTO> scooters = new ArrayList<>();
        for (Scooter s: scooterRepository.findAll()){
            scooters.add(new ScooterDTO(s));
        }
        return scooters;
    }

    public ScooterDTO getScooterById(String id) {
        return scooterRepository.findById(id)
                .map(ScooterDTO::new)
                .orElseThrow(()-> new NotFoundException("Scooter",id));
    }

    public ScooterDTO updateScooter(String id, CreateScooterDTO scooterDTO) {
        Scooter scooter = scooterRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("scooter",id));
        BeanUtils.copyProperties(scooterDTO,scooter,"id");

        Scooter updatedScooter = scooterRepository.save(scooter);
        return new ScooterDTO(updatedScooter);
    }

    public ScooterDTO deleteScooter(String id) {
        Scooter scooter = scooterRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Scooter",id));

        scooterRepository.deleteById(id);
        return new ScooterDTO(scooter);
    }

    // ADMIN CAMBIA ESTADO (mantenimiento/de baja) TODO suena a redundante con el update general, podría reusarse
    public ScooterDTO updateState(String id, ScooterState newState) {
        Scooter scooter = scooterRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("scooter",id));
        scooter.setState(newState);
        Scooter updatedScooter = scooterRepository.save(scooter);
        return new ScooterDTO(updatedScooter);
    }

    //SISTEMA RECIBE INFO NUEVA DEL GPS DEL MONOPATIN
    public ScooterDTO updateTelemetry(String id, TelemetryDTO telemetry) {
        Scooter scooter = scooterRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("scooter",id));
            // actualizar posición
            scooter.setLatitude(telemetry.getLatitude());
            scooter.setLongitude(telemetry.getLongitude());

            // sumar acumulados (si vienen valores)
            if (telemetry.getKmDelta() != null) {
                scooter.setAccumulatedKms(
                        (scooter.getAccumulatedKms() == null ? 0.0 : scooter.getAccumulatedKms()) + telemetry.getKmDelta()
                );
            }
            if (telemetry.getMinutesDelta() != null) {
                scooter.setAccumulatedMinutes(
                        (scooter.getAccumulatedMinutes() == null ? 0L : scooter.getAccumulatedMinutes())
                                + telemetry.getMinutesDelta()
                );
            }
        Scooter updatedScooter = scooterRepository.save(scooter);
        return new ScooterDTO(updatedScooter);
    }

    // PERMITE AL USUARIO VER MONOPATINES CERCA
    public List<NearbyDTO> findNearby(Double lat, Double lon, Double radiusKm) {
        List<Scooter> available = scooterRepository.findByState(ScooterState.AVAILABLE);

        return available.stream()
                // Evita errores si hay scooters sin coordenadas
                .filter(s -> s.getLatitude() != null && s.getLongitude() != null)
                .map(s -> {
                    double distance = distanceKm(lat, lon, s.getLatitude(), s.getLongitude());
                    return new NearbyDTO(
                            s.getId(),
                            s.getSerial(),
                            s.getLatitude(),
                            s.getLongitude(),
                            distance
                    );
                })
                .filter(dto -> dto.getDistanceKm() <= radiusKm)
                .sorted(Comparator.comparingDouble(NearbyDTO::getDistanceKm))
                .collect(Collectors.toList());
    }

    //Metodo auxiliar para calcular distancia entre 2 ubicaciones
    private double distanceKm(Double lat1, Double lon1, Double lat2, Double lon2) {
        if (lat1 == null || lon1 == null || lat2 == null || lon2 == null)
            return Double.MAX_VALUE;

        final int R = 6371; // radio de la Tierra en km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

        public List<ScooterDTO> getScootersByIds(List<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyList();
        }
        List<Scooter> scooters = scooterRepository.findAllById(ids);
        return scooters.stream()
                .map(ScooterDTO::new)
                .collect(Collectors.toList());
    }
}
