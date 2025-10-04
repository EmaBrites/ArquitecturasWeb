package dao.factura_producto;

import dao.factory.MySqlDAOFactory;
import dto.FacturaProductoDTO;
import entities.FacturaProducto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class FacturaProductoImpl implements FacturaProductoDAO {

    private static FacturaProductoImpl instance;
    private final Connection connection;

    private FacturaProductoImpl() throws SQLException {
        connection = MySqlDAOFactory.getConn();
    }

    public static FacturaProductoImpl getInstance() throws SQLException {
        if (instance == null) {
            instance = new FacturaProductoImpl();
        }
        return instance;
    }

    @Override
    public void insert(FacturaProducto facturaProducto) throws SQLException {
        String sql = "INSERT INTO factura_producto (idFactura, idProducto, cantidad) VALUES (?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, facturaProducto.getFacturaId());
        ps.setInt(2, facturaProducto.getProductoId());
        ps.setInt(3, facturaProducto.getCantidad());
        ps.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM factura_producto WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    @Override
    public void update(FacturaProducto facturaProducto) throws SQLException {
        String sql = "UPDATE factura_producto SET cantidad = ? WHERE idProducto = ? AND idFactura = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, facturaProducto.getCantidad());
        ps.setInt(2, facturaProducto.getProductoId());
        ps.setInt(3, facturaProducto.getFacturaId());
        ps.executeUpdate();
    }

    @Override
    public FacturaProductoDTO getById(int idFactura, int idProducto) throws SQLException {
        String sql = "SELECT * FROM factura_producto WHERE idFactura = ? AND idProducto = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, idFactura);
        ps.setInt(2, idProducto);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int facturaId = rs.getInt(FacturaProductoDTO.ID_FACTURA);
            int productoId = rs.getInt(FacturaProductoDTO.ID_PRODUCTO);
            int cantidad = rs.getInt(FacturaProductoDTO.CANTIDAD);
            return new FacturaProductoDTO(facturaId, productoId, cantidad);
        }
        return null;
    }

    @Override
    public List<FacturaProductoDTO> getAll() throws SQLException {
        String sql = "SELECT * FROM factura_producto";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<FacturaProductoDTO> list = new java.util.ArrayList<>();
        while (rs.next()) {
            int facturaId = rs.getInt(FacturaProductoDTO.ID_FACTURA);
            int productoId = rs.getInt(FacturaProductoDTO.ID_PRODUCTO);
            int cantidad = rs.getInt(FacturaProductoDTO.CANTIDAD);
            FacturaProductoDTO facturaProductoDTO = new FacturaProductoDTO(facturaId, productoId, cantidad);
            list.add(facturaProductoDTO);
        }
        return list;
    }
}
