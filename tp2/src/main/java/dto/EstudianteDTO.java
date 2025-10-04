package dto;

import entities.Estudiante;

import java.io.Serializable;

public class EstudianteDTO implements Serializable {
    private final String nombre;
    private final String apellido;
    private final String genero;
    private final int numeroLibreta;
    private final String ciudad;

    public  EstudianteDTO(Estudiante estudiante) {
        nombre = estudiante.getNombre();
        apellido = estudiante.getApellido();
        genero = estudiante.getGenero();
        numeroLibreta = estudiante.getNumeroLibreta();
        ciudad = estudiante.getCiudad();
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getGenero() {
        return genero;
    }

    public int getNumeroLibreta() {
        return numeroLibreta;
    }

    public String getCiudad() {
        return ciudad;
    }

    @Override
    public String toString() {
        return "EstudianteDTO{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", genero='" + genero + '\'' +
                ", numeroLibreta=" + numeroLibreta +
                ", ciudad='" + ciudad + '\'' +
                '}';
    }
}
