package factory;

import repositories.CarreraRepository;
import repositories.EstudianteCarreraRepository;
import repositories.EstudianteRepository;

public abstract class Factory {
    public static final int DERBY = 2;
    public static final int MYSQL = 1;

    public static Factory getFactory(int type) {
        switch (type) {
            case MYSQL:
                return MySQLFactory.getInstance();
            case DERBY:
                throw new UnsupportedOperationException("No se ha implementado la fabrica para Derby");
            default:
                throw new IllegalArgumentException("No se encontro el tipo de Factory");
        }
    }

    public abstract EstudianteRepository getEstudianteRepository();

    public abstract EstudianteCarreraRepository getEstudianteCarreraRepository();

    public abstract CarreraRepository getCarreraRepository();
}
