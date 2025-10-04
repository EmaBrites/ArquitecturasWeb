package dto;

import entities.EstudianteCarrera;

import java.io.Serializable;

public class EstudianteCarreraDTO implements Serializable {
    private final int id;
    private final int inscripcion;
    private final int graduacion;
    private final int antiguedad;
    private final int idCarrera;
    private final String nombreEstudiante;
    private final String nombreCarrera;
    private final String apellidoEstudiante;
    private final String generoEstudiante;
    private final int libretaEstudiante;

    public EstudianteCarreraDTO(EstudianteCarrera estudianteCarrera) {
        this.id = estudianteCarrera.getId();
        this.inscripcion = estudianteCarrera.getInscripcion();
        this.graduacion = estudianteCarrera.getGraduacion();
        this.antiguedad = estudianteCarrera.getAntiguedad();
        this.idCarrera = estudianteCarrera.getCarrera().getId();
        this.nombreEstudiante = estudianteCarrera.getEstudiante().getNombre();
        this.apellidoEstudiante = estudianteCarrera.getEstudiante().getApellido();
        this.generoEstudiante = estudianteCarrera.getEstudiante().getGenero();
        this.libretaEstudiante = estudianteCarrera.getEstudiante().getNumeroLibreta();
        this.nombreCarrera = estudianteCarrera.getCarrera().getNombre();
    }

    public int getId() {
        return id;
    }

    public int getInscripcion() {
        return inscripcion;
    }

    public int getGraduacion() {
        return graduacion;
    }

    public int getAntiguedad() {
        return antiguedad;
    }

    public int getIdCarrera() {
        return idCarrera;
    }

    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    public String getNombreCarrera() {
        return nombreCarrera;
    }

    public String getApellidoEstudiante() {
        return apellidoEstudiante;
    }

    public String getGeneroEstudiante() {
        return generoEstudiante;
    }

    public int getLibretaEstudiante() {
        return libretaEstudiante;
    }

    @Override
    public String toString() {
        return "EstudianteCarreraDTO{" +
                "id=" + id +
                ", inscripcion=" + inscripcion +
                ", graduacion=" + graduacion +
                ", antiguedad=" + antiguedad +
                ", idCarrera=" + idCarrera +
                ", nombreEstudiante='" + nombreEstudiante + '\'' +
                ", nombreCarrera='" + nombreCarrera + '\'' +
                ", apellidoEstudiante='" + apellidoEstudiante + '\'' +
                ", generoEstudiante='" + generoEstudiante + '\'' +
                ", libretaEstudiante=" + libretaEstudiante +
                '}';
    }
}
