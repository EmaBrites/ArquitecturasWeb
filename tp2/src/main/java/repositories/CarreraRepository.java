package repositories;

import dto.CarreraDTO;
import entities.Carrera;

import java.util.List;

public interface CarreraRepository {
    void insert(Carrera carrera);

    void update(Carrera carrera);

    void delete(int id);

    CarreraDTO findById(int id);

    List<CarreraDTO> findAll();
}
