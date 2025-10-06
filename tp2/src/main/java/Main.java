import entities.Carrera;
import entities.Estudiante;
import entities.EstudianteCarrera;
import factory.Factory;
import repositories.CarreraRepository;
import repositories.EstudianteCarreraRepository;
import repositories.EstudianteRepository;
import utils.HelperCSV;

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
//        Estudiante estudiante = new Estudiante("Josefa", "Rodriguez", 98, "Female", 6397408, "La Plata", 123456);
//        estudianteRepository.insert(estudiante);
//        Carrera carrera = new Carrera();
//        carrera.setId(1);
//        estudianteCarreraRepository.insert(new EstudianteCarrera(103, 2019, 2022, 3, estudiante, carrera));
//        carrera.setId(3);
//        estudianteCarreraRepository.insert(new EstudianteCarrera(104, 2018, 2023, 5, estudiante, carrera));
//        System.out.println(estudianteCarreraRepository.findById(103));
//        System.out.println(estudianteCarreraRepository.findById(104));



        //D) Recuperar un estudiante, en base a su número de libreta universitaria.
        //estudianteRepository.findByLibretaUni(72976);

        //E) Recuperar todos los estudiantes, en base a su género.
        //estudianteRepository.findByGenero("male");



    }
}
