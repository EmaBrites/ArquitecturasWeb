package repositories;

import dto.EstudianteDTO;
import entities.Estudiante;

import java.util.List;

public interface EstudianteRepository {
    void insert(Estudiante estudiante);

    void update(Estudiante estudiante);

    void delete(int id);

    EstudianteDTO findById(int id);

    List<EstudianteDTO> findAll();

    List<EstudianteDTO> findAllOrderByApellidoAsc();

    EstudianteDTO findByLibretaUni(int libreta);

    List<EstudianteDTO> findByGenero(String genero);

    List<EstudianteDTO> findByCarreraYCiudad(String carrera, String ciudad);

}
