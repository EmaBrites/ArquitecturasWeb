package utils;

import entities.Carrera;
import entities.Estudiante;
import entities.EstudianteCarrera;
import factory.Factory;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import repositories.CarreraRepository;
import repositories.EstudianteCarreraRepository;
import repositories.EstudianteRepository;

import java.io.FileReader;

public class HelperCSV {
    public static final String CARRERAS = "carreras";
    public static final String CARRERAS_CSV = "src/main/resources/carreras.csv";
    public static final String CARRERA_CSV = "src/main/resources/estudianteCarrera.csv";
    public static final String ESTUDIANTES = "estudiantes";
    public static final String ESTUDIANTES_CSV = "src/main/resources/estudiantes.csv";
    public static final String ESTUDIANTE_CARRERA = "estudiante_carrera";
    private Factory factory;

    public HelperCSV() {
        factory = Factory.getFactory(Factory.MYSQL);
    }

    public void populateDB() {
        System.out.println("Insertando datos...");
        processCSV(ESTUDIANTES_CSV, ESTUDIANTES);
        processCSV(CARRERAS_CSV, CARRERAS);
        processCSV(CARRERA_CSV, ESTUDIANTE_CARRERA);
        System.out.println("Datos insertados");
    }

    private void processCSV(String filePath, String entity) {
        System.out.println("Insertando " + entity + "...");
        try (CSVParser parser = CSVFormat.DEFAULT.withHeader().parse(new FileReader(filePath))) {
            switch (entity.toLowerCase()) {
                case ESTUDIANTES:
                    processEstudiantes(parser);
                    break;
                case CARRERAS:
                    processCarreras(parser);
                    break;
                case ESTUDIANTE_CARRERA:
                    processEstudianteCarrera(parser);
                    break;
                default:
                    throw new IllegalArgumentException("Entidad desconocida: " + entity);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al procesar " + entity + ": " + e.getMessage(), e);
        }
        System.out.println(entity + " insertados");
    }

    private void processEstudianteCarrera(CSVParser parser) {
        EstudianteCarreraRepository estutudianteCarreraRepo = factory.getEstudianteCarreraRepository();
        for (CSVRecord row : parser) {
            try {
                EstudianteCarrera estudianteCarrera = new EstudianteCarrera();
                Estudiante estudiante = new Estudiante();
                estudiante.setDni(Integer.parseInt(row.get("id_estudiante")));
                estudianteCarrera.setEstudiante(estudiante);
                Carrera carrera = new Carrera();
                carrera.setId(Integer.parseInt(row.get("id_carrera")));
                estudianteCarrera.setCarrera(carrera);
                estudianteCarrera.setId(Integer.parseInt(row.get("id")));
                estudianteCarrera.setInscripcion(Integer.parseInt(row.get("inscripcion")));
                estudianteCarrera.setGraduacion(Integer.parseInt(row.get("graduacion")));
                estudianteCarrera.setAntiguedad(Integer.parseInt(row.get("antiguedad")));
                estutudianteCarreraRepo.insert(estudianteCarrera);
            } catch (RuntimeException e) {
                System.out.println("Error al persistir estudiante_carrera " + e.getMessage());
                System.out.println("Record: " + row.toString());
            }
        }
    }

    private void processCarreras(CSVParser parser) {
        CarreraRepository carreraRepo = factory.getCarreraRepository();
        for (CSVRecord row : parser) {
            try {
                Carrera carrera = new Carrera();
                carrera.setId(Integer.parseInt(row.get("id_carrera")));
                carrera.setNombre(row.get("carrera"));
                carrera.setDuracion(Integer.parseInt(row.get("duracion")));
                carreraRepo.insert(carrera);
            } catch (RuntimeException e) {
                System.out.println("Error al persistir carrera " + e.getMessage());
                System.out.println("Record: " + row.toString());
            }
        }
    }

    private void processEstudiantes(CSVParser parser) {
        EstudianteRepository estutudianteRepo = factory.getEstudianteRepository();
        for (CSVRecord row : parser) {
            try {
                Estudiante estudiante = new Estudiante();
                estudiante.setDni(Integer.parseInt(row.get("DNI")));
                estudiante.setNombre(row.get("nombre"));
                estudiante.setApellido(row.get("apellido"));
                estudiante.setEdad(Integer.parseInt(row.get("edad")));
                estudiante.setGenero(row.get("genero"));
                estudiante.setCiudad(row.get("ciudad"));
                estudiante.setNumeroLibreta(Integer.parseInt(row.get("LU")));
                estutudianteRepo.insert(estudiante);
            } catch (RuntimeException e) {
                System.out.println("Error al persistir estudiante " + e.getMessage());
                System.out.println("Record: " + row.toString());
            }
        }
    }

}
