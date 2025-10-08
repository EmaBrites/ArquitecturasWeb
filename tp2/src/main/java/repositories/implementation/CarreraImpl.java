package repositories.implementation;

import dto.CarreraDTO;
import dto.CarreraInscriptosDTO;
import dto.ReporteCarreraDTO;
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
        String nativeQuery = """
                    SELECT
                        c.id AS carreraId,
                        c.nombre AS nombreCarrera,
                        t.anio AS anio,
                        SUM(CASE WHEN t.tipo = 'INSCRIPCION' THEN 1 ELSE 0 END) AS inscriptos,
                        SUM(CASE WHEN t.tipo = 'EGRESO' THEN 1 ELSE 0 END) AS egresados
                    FROM (
                        SELECT ec.idCarrera, ec.inscripcion AS anio, 'INSCRIPCION' AS tipo FROM EstudianteCarrera ec
                        UNION ALL
                        SELECT ec.idCarrera, ec.graduacion AS anio, 'EGRESO' AS tipo 
                        FROM EstudianteCarrera ec 
                        WHERE ec.graduacion IS NOT NULL AND ec.graduacion > 0
                    ) t
                    JOIN Carrera c ON t.idCarrera = c.id
                    GROUP BY c.id, c.nombre, t.anio
                    ORDER BY c.nombre ASC, t.anio ASC
                """;

        List<Object[]> resultados = em.createNativeQuery(nativeQuery).getResultList();
        return resultados.stream()
                .map(row -> new ReporteCarreraDTO(
                        (String) row[1],                // Nombre Carrera
                        ((Number) row[2]).intValue(),   // Año
                        ((Number) row[3]).longValue(),  // Inscriptos
                        ((Number) row[4]).longValue()   // Egresados
                ))
                .toList();
    }
}
