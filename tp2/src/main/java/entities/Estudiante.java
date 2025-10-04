package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Estudiante {
    @Column
    private String nombre;
    @Column
    private String apellido;
    @Column
    private int edad;
    @Column
    private String genero;
    @Column
    private int dni;
    @Column
    private String ciudad;
    @Id
    private int numeroLibreta;
    @OneToMany(mappedBy = "estudiante")
    private List<EstudianteCarrera> estudianteCarreras;

    public Estudiante() {
    }

    public Estudiante(String nombre, String apellido, int edad, String genero, int dni, String ciudad, int numeroLibreta) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.genero = genero;
        this.dni = dni;
        this.ciudad = ciudad;
        this.numeroLibreta = numeroLibreta;
    }

    public Estudiante(String nombre, String apellido, int edad, String genero, int dni, String ciudad, int numeroLibreta, List<EstudianteCarrera> estudianteCarreras) {
        this(nombre, apellido, edad, genero, dni, ciudad, numeroLibreta);
        this.estudianteCarreras = estudianteCarreras;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public int getNumeroLibreta() {
        return numeroLibreta;
    }

    public void setNumeroLibreta(int numeroLibreta) {
        this.numeroLibreta = numeroLibreta;
    }

    public List<EstudianteCarrera> getEstudianteCarreras() {
        return estudianteCarreras;
    }

    public void setEstudianteCarreras(List<EstudianteCarrera> estudianteCarreras) {
        this.estudianteCarreras = estudianteCarreras;
    }

    @Override
    public String toString() {
        return "Estudiante{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", edad=" + edad +
                ", genero='" + genero + '\'' +
                ", dni=" + dni +
                ", ciudad='" + ciudad + '\'' +
                ", numeroLibreta=" + numeroLibreta +
                ", estudianteCarreras=" + estudianteCarreras +
                '}';
    }
}
