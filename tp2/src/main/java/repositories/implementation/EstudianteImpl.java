package repositories.implementation;

import dto.EstudianteDTO;
import entities.Estudiante;
import factory.MySQLFactory;
import jakarta.persistence.EntityManager;
import repositories.EstudianteRepository;

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
}
