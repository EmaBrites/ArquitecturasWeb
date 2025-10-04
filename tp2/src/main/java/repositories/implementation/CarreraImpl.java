package repositories.implementation;

import dto.CarreraDTO;
import entities.Carrera;
import factory.MySQLFactory;
import jakarta.persistence.EntityManager;
import repositories.CarreraRepository;

import java.util.List;

public class CarreraImpl implements CarreraRepository {
    private static CarreraImpl instance;
    private final EntityManager em;

    private CarreraImpl() {
        em = MySQLFactory.getEntityManager();
    }

    public static CarreraImpl getInstance() {
        if (instance == null) {
            instance = new CarreraImpl();
        }
        return instance;
    }

    @Override
    public void insert(Carrera carrera) {
        em.getTransaction().begin();
        em.persist(carrera);
        em.getTransaction().commit();
    }

    @Override
    public void update(Carrera carrera) {
        em.getTransaction().begin();
        em.merge(carrera);
        em.getTransaction().commit();
    }

    @Override
    public void delete(int id) {
        Carrera carrera = em.find(Carrera.class, id);
        if (carrera != null) {
            em.getTransaction().begin();
            em.remove(carrera);
            em.getTransaction().commit();
        }
    }

    @Override
    public CarreraDTO findById(int id) {
        return new CarreraDTO(em.find(Carrera.class, id));
    }

    @Override
    public List<CarreraDTO> findAll() {
        List<Carrera> carreras = em.createQuery("FROM Carrera", Carrera.class).getResultList();
        return carreras.stream().map(CarreraDTO::new).toList();
    }
}
