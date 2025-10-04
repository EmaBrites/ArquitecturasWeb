package dto;

public class FacturaDTO {

    public static final String ID = "idFactura";
    public static final String CLIENTE_ID = "idCliente";
    private final int id;
    private final int clienteId;

    public  FacturaDTO(int id, int clienteId) {
        this.id = id;
        this.clienteId = clienteId;
    }

    public int getId() {
        return id;
    }

    public int getClienteId() {
        return clienteId;
    }

    public String toString() {
        return "FacturaDTO{id=" + id + ", clienteId=" + clienteId + "}";
    }
}
