package dto;

public class ClienteDTO {

    public static final String ID = "idCliente";
    public static final String NOMBRE = "nombre";
    public static final String EMAIL = "email";
    private final int id;
    private final String nombre;
    private final String email;
    private double totalFacturado;

    public ClienteDTO(int id, String nombre, String email) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public double getTotalFacturado() {
        return totalFacturado;
    }

    public void setTotalFacturado(double totalFacturado) {
        this.totalFacturado = totalFacturado;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", totalFacturado=" + totalFacturado +
                '}';
    }
}
