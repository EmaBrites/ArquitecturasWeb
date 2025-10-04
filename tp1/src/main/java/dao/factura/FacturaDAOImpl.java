package dao.factura;

import dao.factory.MySqlDAOFactory;
import dto.FacturaDTO;
import entities.Factura;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FacturaDAOImpl implements FacturaDAO {

    private static FacturaDAOImpl instance;
    private final Connection connection;

    private FacturaDAOImpl() throws SQLException {
        connection = MySqlDAOFactory.getConn();
    }

    public static FacturaDAOImpl getInstance() throws SQLException {
        if (instance == null) {
            instance = new FacturaDAOImpl();
        }
        return instance;
    }

    @Override
    public void insert(Factura factura) throws SQLException {
        String sql = "INSERT INTO factura VALUES (?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, factura.getId());
        ps.setInt(2, factura.getClienteId());
        ps.executeUpdate();
    }

    @Override
    public void update(Factura factura) throws SQLException {
        String sql = "UPDATE factura SET clienteId = ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, factura.getClienteId());
        ps.setInt(2, factura.getId());
        ps.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM factura WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    @Override
    public List<FacturaDTO> getAll() throws SQLException {
        List<FacturaDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM factura ORDER BY clienteId DESC";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt(FacturaDTO.ID);
            int clienteId = rs.getInt(FacturaDTO.CLIENTE_ID);
            FacturaDTO facturaDTO = new FacturaDTO(id, clienteId);
            list.add(facturaDTO);
        }
        return list;
    }

    @Override
    public FacturaDTO getById(int id) throws SQLException {
        String sql = "SELECT * FROM factura WHERE idFactura = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int clienteId = rs.getInt(FacturaDTO.CLIENTE_ID);
            return new FacturaDTO(id, clienteId);
        }
        return null;
    }
}
