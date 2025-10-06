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
    }
}
