package dao.cliente;

import dao.factory.MySqlDAOFactory;
import entities.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClienteDAOImpl implements ClienteDAO {

    private static ClienteDAOImpl instance;
    private final Connection conn;

    public ClienteDAOImpl() throws SQLException {
        this.conn = MySqlDAOFactory.getConn();
    }

    public static ClienteDAO getInstance() throws SQLException {
        if (instance == null) {
            instance = new ClienteDAOImpl();
        }
        return instance;
    }

    @Override
    public void insert(Cliente c) {
        String query = "INSERT INTO clientes (idCliente, nombre, email) VALUES (?, ?, ?)";
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, c.getId());
            ps.setString(2, c.getNombre());
            ps.setString(3, c.getEmail());
            ps.executeUpdate();
        } catch (
                SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
