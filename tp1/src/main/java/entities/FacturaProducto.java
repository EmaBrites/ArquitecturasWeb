package entities;

public class FacturaProducto {
    private int facturaId;
    private int productoId;
    private int cantidad;

    public FacturaProducto(int facturaId, int productoId, int cantidad) {
        this.facturaId = facturaId;
        this.productoId = productoId;
        this.cantidad = cantidad;
    }

    public int getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(int facturaId) {
        this.facturaId = facturaId;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
