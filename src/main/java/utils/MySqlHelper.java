package utils;

import dao.cliente.ClienteDAO;
import dao.cliente.ClienteDAOImpl;
import dao.factory.MySqlDAOFactory;
import entities.Cliente;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MySqlHelper {
    private Connection conn = null;

    public MySqlHelper() {//Constructor

        try {
            conn = MySqlDAOFactory.getConn();
            conn.setAutoCommit(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        if (conn != null){
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void dropTables() throws SQLException {
        dropTable("facturas_productos"); // La factura-producto se debe eliminar antes que las tablas factura y producto
        dropTable("facturas"); // La factura se debe eliminar antes que el cliente
        dropTable("clientes");
        dropTable("productos");
        this.conn.commit();
        System.out.println("Tablas eliminadas");
    }

    private void dropTable(String tableName) throws SQLException {

        if (conn == null || conn.isClosed()) {
            throw new SQLException("La conexión a la base de datos no está abierta.");
        }

        String dropSQL = "DROP TABLE IF EXISTS "+ tableName;

        try (PreparedStatement stmt = conn.prepareStatement(dropSQL)){
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createTables() throws SQLException {
        createTable("CREATE TABLE IF NOT EXISTS clientes(" +
                "idCliente INT NOT NULL, " +
                "nombre VARCHAR(500), " +
                "email VARCHAR(150), " +
                "CONSTRAINT clientes_pk PRIMARY KEY (idCliente));");

        createTable("CREATE TABLE IF NOT EXISTS productos(" +
                "idProducto INT NOT NULL, " +
                "nombre VARCHAR(45), " +
                "valor FLOAT(10,2) NOT NULL, " +
                "CONSTRAINT productos_pk PRIMARY KEY (idProducto));");

        createTable("CREATE TABLE IF NOT EXISTS facturas(" +
                "idFactura INT NOT NULL, " +
                "idCliente INT NOT NULL, " +
                "CONSTRAINT facturas_pk PRIMARY KEY (idFactura), "+
                "CONSTRAINT FK_idCliente FOREIGN KEY (idCliente) REFERENCES clientes (idCliente));");

        createTable("CREATE TABLE IF NOT EXISTS facturas_productos(" +
                "idFactura INT NOT NULL, " +
                "idProducto INT NOT NULL, " +
                "cantidad INT NOT NULL, " +
                "CONSTRAINT facturas_productos_pk PRIMARY KEY (idFactura, idProducto), " +
                "CONSTRAINT FK_idFactura FOREIGN KEY (idFactura) REFERENCES facturas (idFactura), " +
                "CONSTRAINT FK_idProducto FOREIGN KEY (idProducto) REFERENCES productos (idProducto));");

        this.conn.commit();
        System.out.println("Tablas creadas");
    }

    private void createTable(String sql) throws SQLException {
        if (conn == null || conn.isClosed()) {
            throw new SQLException("La conexión a la base de datos no está abierta.");
        }

        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void populateDB() throws SQLException {
            System.out.println("Populating DB...");
            conn.setAutoCommit(false); // Desactiva autocommit para manejar las transacciones manualmente
            processCSV("src\\main\\resources\\clientes.csv", "Cliente");
            conn.commit();
            System.out.println("Datos insertados correctamente");
    }

    private void processCSV(String filePath, String entity){
        System.out.println("Insertando "+ entity.toLowerCase() + "s...");

        try (CSVParser parser = CSVFormat.DEFAULT.withHeader().parse(new FileReader(filePath))){
            processClientes(parser);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void processClientes(CSVParser parser) throws SQLException {
        ClienteDAO clienteDAO = ClienteDAOImpl.getInstance();

        for(CSVRecord row : parser) {
            if(row.size() >= 3) {
                String idString = row.get(0);
                String nombre = row.get(1);
                String email = row.get(2);
                if(isValidNumber(idString) && !nombre.isEmpty() && !email.isEmpty()) {
                    try {
                        int id = Integer.parseInt(idString);
                        Cliente cliente = new Cliente(id, nombre, email);
                        clienteDAO.insert(cliente);
                    } catch (Exception e) {
                        System.err.println("Error al persistir el cliente: " + e.getMessage());
                    }
                }
            }
        }
        System.out.println("Clientes insertados");

    }

    private boolean isValidNumber(String str) {

        if (str == null || str.isEmpty()) return false;

        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
