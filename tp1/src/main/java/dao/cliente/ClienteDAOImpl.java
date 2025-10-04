package dao.cliente;

import dao.factory.MySqlDAOFactory;
import dto.ClienteDTO;
import entities.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAOImpl implements ClienteDAO {

    private static ClienteDAOImpl instance;
    private final Connection conn;

    private ClienteDAOImpl() throws SQLException {
        this.conn = MySqlDAOFactory.getConn();
    }

    public static ClienteDAO getInstance() throws SQLException {
        if (instance == null) {
            instance = new ClienteDAOImpl();
        }
        return instance;
    }

    @Override
    public void insert(Cliente c) throws SQLException {
        String query = "INSERT INTO clientes (idCliente, nombre, email) VALUES (?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, c.getId());
        ps.setString(2, c.getNombre());
        ps.setString(3, c.getEmail());
        ps.executeUpdate();
    }

    @Override
    public List<ClienteDTO> getAll() throws SQLException {
        List<ClienteDTO> list = new ArrayList<>();
        String query = "SELECT * FROM clientes";
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt(ClienteDTO.ID);
            String nombre = rs.getString(ClienteDTO.NOMBRE);
            String email = rs.getString(ClienteDTO.EMAIL);
            ClienteDTO clienteDTO = new ClienteDTO(id, nombre, email);
            list.add(clienteDTO);
        }
        return list;
    }

    @Override
    public ClienteDTO getById(int id) throws SQLException {
        String query = "SELECT * FROM clientes WHERE idCliente = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            String nombre = rs.getString(ClienteDTO.NOMBRE);
            String email = rs.getString(ClienteDTO.EMAIL);
            return new ClienteDTO(id, nombre, email);
        }
        return null;
    }

    @Override
    public void update(Cliente c) throws SQLException {
        String query = "UPDATE clientes SET nombre = ?, email = ? WHERE idCliente = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, c.getNombre());
        ps.setString(2, c.getEmail());
        ps.setInt(3, c.getId());
        ps.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM clientes WHERE idCliente = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    @Override
    public List<ClienteDTO> getClientesOrdenadosPorFacturacion() throws SQLException {
        List<ClienteDTO> clientes = new ArrayList<>();

        String query = "SELECT c.idCliente, c.nombre, c.email, " +
                "       SUM(p.valor * fp.cantidad) AS total " +
                "FROM clientes c " +
                "JOIN facturas f ON c.idCliente = f.idCliente " +
                "JOIN facturas_productos fp ON f.idFactura = fp.idFactura " +
                "JOIN productos p ON fp.idProducto = p.idProducto " +
                "GROUP BY c.idCliente, c.nombre, c.email " +
                "ORDER BY total DESC";

        try (PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ClienteDTO cliente = new ClienteDTO(rs.getInt("idCliente"),
                        rs.getString("nombre"),
                        rs.getString("email"));
                cliente.setTotalFacturado(rs.getDouble("total"));
                clientes.add(cliente);
            }
        }
        return clientes;
    }

}
