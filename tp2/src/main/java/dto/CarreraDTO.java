package dto;

import entities.Carrera;

import java.io.Serializable;

public class CarreraDTO implements Serializable {
    private final int id;
    private final String nombre;
    private final int duracion;

    public CarreraDTO(Carrera carrera) {
        this.id = carrera.getId();
        this.nombre = carrera.getNombre();
        this.duracion = carrera.getDuracion();
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getDuracion() {
        return duracion;
    }

    public String toString() {
        return "CarreraDTO{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", duracion=" + duracion +
                '}';
    }
}
