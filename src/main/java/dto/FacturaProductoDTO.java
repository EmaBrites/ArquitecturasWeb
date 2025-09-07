package dto;

public class FacturaProductoDTO {
    public static final String ID_FACTURA = "idFactura";
    public static final String ID_PRODUCTO = "idProducto";
    public static final String CANTIDAD = "cantidad";
    private final int idFactura;
    private final int idProducto;
    private final int cantidad;

    public FacturaProductoDTO(int idFactura, int idProducto, int cantidad) {
        this.idFactura = idFactura;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
    }

    public int getIdFactura() {
        return idFactura;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    @Override
    public String toString() {
        return "FacturaProductoDTO{" +
                "idFactura=" + idFactura +
                ", idProducto=" + idProducto +
                ", cantidad=" + cantidad +
                '}';
    }
}
