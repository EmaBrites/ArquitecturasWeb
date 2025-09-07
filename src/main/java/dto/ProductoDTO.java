package dto;

public class ProductoDTO {

    public static final String ID = "id";
    public static final String NOMBRE = "nombre";
    public static final String VALOR = "valor";
    private final int id;
    private final String nombre;
    private final float valor;

    public ProductoDTO(int id, String nombre, float precio) {
        this.id = id;
        this.nombre = nombre;
        this.valor = precio;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public float getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return "ProductoDTO{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", precio=" + valor +
                '}';
    }
}
