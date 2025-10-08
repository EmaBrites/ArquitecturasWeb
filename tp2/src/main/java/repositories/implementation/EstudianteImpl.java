package repositories.implementation;

import dto.EstudianteDTO;
import entities.Estudiante;
import factory.MySQLFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import repositories.EstudianteRepository;

import java.util.ArrayList;
import java.util.List;

public class EstudianteImpl implements EstudianteRepository {
    private static EstudianteImpl instance;
    private final EntityManager em;

    private EstudianteImpl() {
        em = MySQLFactory.getEntityManager();
    }

    public static EstudianteImpl getInstance() {
        if (instance == null) {
            instance = new EstudianteImpl();
        }
        return instance;
    }

    @Override
    public void insert(Estudiante estudiante) {
        em.getTransaction().begin();
        em.persist(estudiante);
        em.getTransaction().commit();
    }

    @Override
    public void update(Estudiante estudiante) {
        em.getTransaction().begin();
        em.merge(estudiante);
        em.getTransaction().commit();
    }

    @Override
    public void delete(int id) {
        Estudiante estudiante = em.find(Estudiante.class, id);
        if (estudiante != null) {
            em.getTransaction().begin();
            em.remove(estudiante);
            em.getTransaction().commit();
        }
    }

    @Override
    public EstudianteDTO findById(int id) {
        return new EstudianteDTO(em.find(Estudiante.class, id));
    }

    @Override
    public List<EstudianteDTO> findAll() {
        List<Estudiante> estudiantes = em.createQuery("FROM Estudiante", Estudiante.class).getResultList();
        return estudiantes.stream().map(EstudianteDTO::new).toList();
    }

    @Override
    public EstudianteDTO findByLibretaUni(int nroLibreta) {
        try {
            Estudiante estudiante = em.createQuery(
                            "SELECT e FROM Estudiante e WHERE e.numeroLibreta = :nro", Estudiante.class)
                    .setParameter("nro", nroLibreta)
                    .getSingleResult();

            EstudianteDTO dto = new EstudianteDTO(estudiante);
            return dto;
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<EstudianteDTO> findByGenero(String genero) {
        List<Estudiante> estudiantes = em.createQuery(
                        "SELECT e FROM Estudiante e WHERE e.genero = :genero", Estudiante.class)
                .setParameter("genero", genero)
                .getResultList();
        List<EstudianteDTO> dtos = estudiantes.stream()
                .map(EstudianteDTO::new)
                .toList();
        return dtos;
    }


    @Override
    public List<EstudianteDTO> findAllOrderByApellidoAsc() {
        List<Estudiante> estudiantes = em.createQuery(
                "SELECT e FROM Estudiante e ORDER BY e.apellido ASC, e.nombre ASC",
                Estudiante.class
        ).getResultList();

        return estudiantes.stream()
                .map(EstudianteDTO::new)
                .toList();
    }

    @Override
    public List<EstudianteDTO> findByCarreraYCiudad(String carrera, String ciudad){
        List<Estudiante> estudiantes = em.createQuery(
                "SELECT e FROM Estudiante e WHERE "+
                        "e.ciudad = :ciudad AND " +
                        "e.dni IN(" +
                        "SELECT ec.estudiante.id FROM EstudianteCarrera ec WHERE ec.carrera.id = (" +
                        "SELECT c.id FROM Carrera c WHERE c.nombre = :carrera))", Estudiante.class)
                .setParameter("carrera",carrera)
                .setParameter("ciudad",ciudad)
                .getResultList();
        return estudiantes.stream()
                .map(EstudianteDTO::new)
                .toList();
    }

}
