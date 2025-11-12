package com.exa.stopservice.service;

import com.exa.stopservice.Exception.NotFoundException;
import com.exa.stopservice.dto.CreateStopDTO;
import com.exa.stopservice.dto.StopDTO;
import com.exa.stopservice.entity.Stop;
import com.exa.stopservice.repository.StopRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class StopService {

    private final StopRepository stopRepository;

    public StopService(StopRepository stopRepository){this.stopRepository=stopRepository; }

    public StopDTO createStop(CreateStopDTO stopDTO){
        Stop stop = new Stop();
        BeanUtils.copyProperties(stopDTO, stop);

        Stop newStop = stopRepository.save(stop);
        return new StopDTO(newStop);
    }

    @Transactional(readOnly = true) // Para que no guarde el estado y tengamos un mejor rendimiento de la consulta.
    public StopDTO getStopById(int id){
        return stopRepository.findById(id)
                .map(StopDTO::new)
                .orElseThrow(()->new NotFoundException("Stop", id));
    }

    @Transactional(readOnly = true)
    public List<StopDTO> getStops(){
        return stopRepository.findAll()
                .stream().map(StopDTO::new).toList();
    }

    public StopDTO updateStop(int id, CreateStopDTO stopDTO){
        Stop stop = stopRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Stop", id));
        BeanUtils.copyProperties(stopDTO,stop);

        Stop updatedStop = stopRepository.save(stop);
        return new StopDTO(updatedStop);
    }

    public StopDTO deleteStop(int id){
        Stop stop = stopRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Stop", id));
        stopRepository.delete(stop);
        return new StopDTO(stop);
    }

    public boolean isStop(double latitude, double longitude){
        Stop stop = stopRepository.getStopByCoordinates(latitude,longitude);
        if (stop!=null){
            return true;
        }
        return false;
    }
}
