package repositories.implementation;

import dto.EstudianteCarreraDTO;
import entities.Carrera;
import entities.Estudiante;
import entities.EstudianteCarrera;
import factory.MySQLFactory;
import jakarta.persistence.EntityManager;
import repositories.EstudianteCarreraRepository;

import java.util.List;

public class EstudianteCarreraImpl implements EstudianteCarreraRepository {
    private static EstudianteCarreraImpl instance;
    private final EntityManager em;

    private EstudianteCarreraImpl() {
        em = MySQLFactory.getEntityManager();
    }

    public static EstudianteCarreraImpl getInstance() {
        if (instance == null) {
            instance = new EstudianteCarreraImpl();
        }
        return instance;
    }

    @Override
    public void insert(EstudianteCarrera estudianteCarrera) {
        try {
            em.getTransaction().begin();
            Estudiante estudiante = em.find(Estudiante.class, estudianteCarrera.getEstudiante().getDni());
            estudianteCarrera.setEstudiante(estudiante);
            Carrera carrera = em.find(Carrera.class, estudianteCarrera.getCarrera().getId());
            estudianteCarrera.setCarrera(carrera);
            em.persist(estudianteCarrera);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Error inserting EstudianteCarrera: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(EstudianteCarrera estudianteCarrera) {
        em.getTransaction().begin();
        em.merge(estudianteCarrera);
        em.getTransaction().commit();
    }

    @Override
    public void delete(int id) {
        EstudianteCarrera estudianteCarrera = em.find(EstudianteCarrera.class, id);
        if (estudianteCarrera != null) {
            em.getTransaction().begin();
            em.remove(estudianteCarrera);
            em.getTransaction().commit();
        }
    }

    @Override
    public EstudianteCarreraDTO findById(int id) {
        return new EstudianteCarreraDTO(em.find(EstudianteCarrera.class, id));
    }

    @Override
    public List<EstudianteCarreraDTO> findAll() {
        List<EstudianteCarrera> estudianteCarreraList = em.createQuery("FROM EstudianteCarrera", EstudianteCarrera.class).getResultList();
        return estudianteCarreraList.stream().map(EstudianteCarreraDTO::new).toList();
    }
}
