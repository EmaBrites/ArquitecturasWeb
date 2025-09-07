package entities;

public class Factura {
    private int id;
    private int clienteId;

    public Factura(int id, int clienteId) {
        this.id = id;
        this.clienteId = clienteId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

}
