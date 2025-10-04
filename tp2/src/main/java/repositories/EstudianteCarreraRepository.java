package repositories;

import dto.EstudianteCarreraDTO;
import entities.EstudianteCarrera;

import java.util.List;

public interface EstudianteCarreraRepository {
    void insert(EstudianteCarrera estudianteCarrera);

    void update(EstudianteCarrera estudianteCarrera);

    void delete(int id);

    EstudianteCarreraDTO findById(int id);

    List<EstudianteCarreraDTO> findAll();
}
