import dto.EstudianteDTO;
import dto.ReporteCarreraDTO;
import entities.Carrera;
import entities.Estudiante;
import entities.EstudianteCarrera;
import factory.Factory;
import repositories.CarreraRepository;
import repositories.EstudianteCarreraRepository;
import repositories.EstudianteRepository;
import utils.HelperCSV;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        HelperCSV helperCSV = new HelperCSV();
        helperCSV.populateDB();
        Factory factory = Factory.getFactory(Factory.MYSQL);
        EstudianteRepository estudianteRepository = factory.getEstudianteRepository();
        CarreraRepository carreraRepository = factory.getCarreraRepository();
        EstudianteCarreraRepository estudianteCarreraRepository = factory.getEstudianteCarreraRepository();
        //System.out.println(estudianteRepository.findAll());
        //System.out.println(carreraRepository.findAll());
        //System.out.println(estudianteCarreraRepository.findAll());

        //A) dar de alta un estudiante
        Estudiante estudiante = new Estudiante("Josefa", "Rodriguez", 98, "Female", 6397408, "La Plata", 123456);
        estudianteRepository.insert(estudiante);
        Carrera carrera = new Carrera();
        carrera.setId(1);

        //B) matricular un estudiante en una carrera
        estudianteCarreraRepository.insert(new EstudianteCarrera(103, 2019, 2022, 3, estudiante, carrera));
        carrera.setId(3);
        estudianteCarreraRepository.insert(new EstudianteCarrera(104, 2018, 2023, 5, estudiante, carrera));
        System.out.println(estudianteCarreraRepository.findById(103));
        System.out.println(estudianteCarreraRepository.findById(104));

        // C) Recuperar todos los estudiantes ordenados por apellido
        System.out.println("\n\n-----------Ejercicio 2-C-----------");
        estudianteRepository.findAllOrderByApellidoAsc().forEach(System.out::println);

        //D) Recuperar un estudiante, en base a su número de libreta universitaria.
        System.out.println("\n\n-----------Ejercicio 2-D-----------");
        EstudianteDTO estudianteLibreta = estudianteRepository.findByLibretaUni(72976);
        System.out.println(estudianteLibreta.toString());

        //E) Recuperar todos los estudiantes, en base a su género.
        System.out.println("\n\n-----------Ejercicio 2-E-----------");
        String genero = "male";
        List<EstudianteDTO> estudiantes = estudianteRepository.findByGenero(genero);
        estudiantes.forEach(System.out::println);

        // F) System.out.println("=== Carreras ordenadas por cantidad de inscriptos ===");
        System.out.println("\n\n-----------Ejercicio 2-F-----------");
        carreraRepository.findAllOrderByInscriptosDesc().forEach(System.out::println);

        //G recuperar los estudiantes de una determinada carrera, filtrado por ciudad de residencia.
        List<EstudianteDTO> estudiantesFiltrados = estudianteRepository.findByCarreraYCiudad("TUDAI","Rauch");
        System.out.println("\n\n-----------Ejercicio 2-G-----------");
        estudiantesFiltrados.forEach(System.out::println);

        //3)
        System.out.println("\n\n-----------Ejercicio 3-----------");
        List<ReporteCarreraDTO> reportes = carreraRepository.reporteCarrera();
        reportes.forEach(System.out::println);
        System.out.println("--- Reporte Finalizado. Total de filas: " + reportes.size() + " ---");
    }
}
