package repositories.implementation;

import dto.CarreraDTO;
import dto.CarreraInscriptosDTO;
import dto.ReporteCarreraDTO;
import entities.Carrera;
import factory.MySQLFactory;
import jakarta.persistence.EntityManager;
import repositories.CarreraRepository;

import java.util.ArrayList;
import java.util.Comparator;
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

    @Override
    public List<CarreraInscriptosDTO> findAllOrderByInscriptosDesc() {
        return em.createQuery(
                "SELECT new dto.CarreraInscriptosDTO(c.nombre, COUNT(ec)) " +
                        "FROM EstudianteCarrera ec JOIN ec.carrera c " +
                        "GROUP BY c.nombre " +
                        "ORDER BY COUNT(ec) DESC",
                CarreraInscriptosDTO.class
        ).getResultList();
    }

    @Override
    public List<ReporteCarreraDTO> reporteCarrera() {
        // 1️⃣ Traemos los inscriptos
        List<ReporteCarreraDTO> inscriptos = em.createQuery("""
        SELECT new dto.ReporteCarreraDTO(
            c.id,
            c.nombre,
            ec.inscripcion,
            COUNT(ec.estudiante),
            0L
        )
        FROM EstudianteCarrera ec
        JOIN ec.carrera c
        GROUP BY c.id, c.nombre, ec.inscripcion
    """, ReporteCarreraDTO.class).getResultList();

        // 2️⃣ Traemos los egresados
        List<ReporteCarreraDTO> egresados = em.createQuery("""
        SELECT new dto.ReporteCarreraDTO(
            c.id,
            c.nombre,
            ec.graduacion,
            0L,
            COUNT(ec.estudiante)
        )
        FROM EstudianteCarrera ec
        JOIN ec.carrera c
        WHERE ec.graduacion IS NOT NULL
        GROUP BY c.id, c.nombre, ec.graduacion
    """, ReporteCarreraDTO.class).getResultList();

        // 3️⃣ Mapeamos carrera → (año → DTO)
        Map<Long, Map<Integer, ReporteCarreraDTO>> carreras = new HashMap<>();

        // Agregamos inscriptos
        for (ReporteCarreraDTO r : inscriptos) {
            carreras
                    .computeIfAbsent(r.getCarreraId(), id -> new HashMap<>())
                    .put(r.getAnio(), r);
        }

        // Sumamos egresados
        for (ReporteCarreraDTO r : egresados) {
            carreras
                    .computeIfAbsent(r.getCarreraId(), id -> new HashMap<>())
                    .merge(
                            r.getAnio(),
                            r,
                            (base, nuevo) -> {
                                base.setCantidadEgresados(base.getCantidadEgresados() + nuevo.getCantidadEgresados());
                                return base;
                            }
                    );
        }

        // 4️⃣ Convertimos a lista ordenada
        return carreras.values().stream()
                .flatMap(m -> m.values().stream())
                .sorted(Comparator
                        .comparing(ReporteCarreraDTO::getNombreCarrera, String.CASE_INSENSITIVE_ORDER)
                        .thenComparing(ReporteCarreraDTO::getAnio))
                .toList();
    }
    rbr-qpbe-sde

}
