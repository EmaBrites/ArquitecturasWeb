package factory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import repositories.CarreraRepository;
import repositories.EstudianteCarreraRepository;
import repositories.EstudianteRepository;
import repositories.implementation.CarreraImpl;
import repositories.implementation.EstudianteCarreraImpl;
import repositories.implementation.EstudianteImpl;

public class MySQLFactory extends Factory {
    private static EntityManagerFactory emf;
    private static MySQLFactory instance;

    private MySQLFactory() {
        emf = Persistence.createEntityManagerFactory("tpJPA");
    }

    public static Factory getInstance() {
        if (instance == null) {
            instance = new MySQLFactory();
        }
        return instance;
    }

    public static EntityManager getEntityManager() {
        getInstance();
        return emf.createEntityManager();
    }

    @Override
    public EstudianteRepository getEstudianteRepository() {
        return EstudianteImpl.getInstance();
    }

    @Override
    public EstudianteCarreraRepository getEstudianteCarreraRepository() {
        return EstudianteCarreraImpl.getInstance();
    }

    @Override
    public CarreraRepository getCarreraRepository() {
        return CarreraImpl.getInstance();
    }

}
