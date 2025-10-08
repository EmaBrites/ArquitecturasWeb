package dto;

public class ReporteCarreraDTO {
    private String nombreCarrera;
    private int anio;
    private long cantidadInscriptos;
    private long cantidadEgresados;

    public ReporteCarreraDTO(String nombreCarrera, int anio, long cantidadInscriptos, long cantidadEgresados) {
        this.nombreCarrera = nombreCarrera;
        this.anio = anio;
        this.cantidadInscriptos = cantidadInscriptos;
        this.cantidadEgresados = cantidadEgresados;
    }

    public String getNombreCarrera() {
        return nombreCarrera;
    }

    public int getAnio() {
        return anio;
    }

    public long getCantidadInscriptos() {
        return cantidadInscriptos;
    }

    public long getCantidadEgresados() {
        return cantidadEgresados;
    }

    @Override
    public String toString() {
        return String.format(
                "| %-30s | Año: %-4d | Inscriptos: %-5d | Egresados: %-5d |",
                nombreCarrera, anio, cantidadInscriptos, cantidadEgresados
        );
    }
}