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
            System.out.println("Estudiante encontrado: " + dto.getNombre() + " " +dto.getApellido()+ " " + dto.getNumeroLibreta());
            return dto;
        } catch (NoResultException e) {
            System.out.println(" No se encontró ningún estudiante con número de libreta: " + nroLibreta);
            return null;
        }
    }

    @Override
    public List<EstudianteDTO> findByGenero(String genero) {
        List<Estudiante> estudiantes = em.createQuery(
                        "SELECT e FROM Estudiante e WHERE e.genero = :genero", Estudiante.class)
                .setParameter("genero", genero)
                .getResultList();
        if (estudiantes.isEmpty()) {
            System.out.println("No se encontraron estudiantes con género: " + genero);
            return new ArrayList<>();
        }
        List<EstudianteDTO> dtos = estudiantes.stream()
                .map(EstudianteDTO::new)
                .toList();
        System.out.println("Estudiantes encontrados del genero " + genero + ":");
        dtos.forEach(dto -> System.out.println(dto.getNombre() + " " + dto.getApellido() + " " + dto.getGenero()));
        return dtos;
    }

}
