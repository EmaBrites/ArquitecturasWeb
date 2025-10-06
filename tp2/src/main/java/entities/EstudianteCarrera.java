package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class EstudianteCarrera {
    @Id
    private int id;
    @Column
    private int inscripcion;
    @Column
    private int graduacion;
    @Column
    private int antiguedad;
    @ManyToOne(optional = false)
    @JoinColumn(name = "idEstudiante", referencedColumnName = "dni")
    private Estudiante estudiante;
    @ManyToOne(optional = false)
    @JoinColumn(name = "idCarrera", referencedColumnName = "id")
    private Carrera carrera;

    public EstudianteCarrera() {
    }

    public EstudianteCarrera(int id, int inscripcion, int graduacion, int antiguedad, Estudiante estudiante, Carrera carrera) {
        this.id = id;
        this.inscripcion = inscripcion;
        this.graduacion = graduacion;
        this.antiguedad = antiguedad;
        this.estudiante = estudiante;
        this.carrera = carrera;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInscripcion() {
        return inscripcion;
    }

    public void setInscripcion(int inscripcion) {
        this.inscripcion = inscripcion;
    }

    public int getGraduacion() {
        return graduacion;
    }

    public void setGraduacion(int graduacion) {
        this.graduacion = graduacion;
    }

    public int getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(int antiguedad) {
        this.antiguedad = antiguedad;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    @Override
    public String toString() {
        return "EstudianteCarrera{" +
                "id=" + id +
                ", inscripcion=" + inscripcion +
                ", graduacion=" + graduacion +
                ", antiguedad=" + antiguedad +
                ", estudiante=" + estudiante +
                ", carrera=" + carrera +
                '}';
    }
}
