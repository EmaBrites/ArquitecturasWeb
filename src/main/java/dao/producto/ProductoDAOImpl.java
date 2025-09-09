package dao.producto;

import dao.factory.MySqlDAOFactory;
import dto.ProductoDTO;
import entities.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProductoDAOImpl implements ProductoDAO {

    private static ProductoDAOImpl instance;
    private final Connection connection;

    private ProductoDAOImpl() throws SQLException {
        this.connection = MySqlDAOFactory.getConn();
    }

    public static ProductoDAOImpl getInstance() throws SQLException {
        if (instance == null) {
            instance = new ProductoDAOImpl();
        }
        return instance;
    }

    // --- SQL de la parte 3 ---
    private static final String SQL_TOP_1 =
            "SELECT p.idProducto, p.nombre, SUM(fp.cantidad * p.valor) AS recaudacion " +
                    "FROM facturas_productos fp " +
                    "JOIN productos p ON p.idProducto = fp.idProducto " +
                    "GROUP BY p.idProducto, p.nombre " +
                    "ORDER BY recaudacion DESC " +
                    "LIMIT 1";

    @Override
    public void insert(Producto producto) throws SQLException {
        String sql = "INSERT INTO producto (id, nombre, precio) VALUES (?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, producto.getId());
        ps.setString(2, producto.getNombre());
        ps.setDouble(3, producto.getValor());
        ps.executeUpdate();
    }

    @Override
    public void update(Producto producto) throws SQLException {
        String sql = "UPDATE producto SET nombre = ?, precio = ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, producto.getNombre());
        ps.setDouble(2, producto.getValor());
        ps.setInt(3, producto.getId());
        ps.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM producto WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    @Override
    public ProductoDTO getById(int id) throws SQLException {
        String sql = "SELECT * FROM producto WHERE idProducto = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            String nombre = rs.getString(ProductoDTO.NOMBRE);
            float valor = rs.getFloat(ProductoDTO.VALOR);
            return new ProductoDTO(id, nombre, valor);
        } else {
            return null;
        }
    }

    @Override
    public ProductoDTO findProductoQueMasRecaudo() {
        try (
             PreparedStatement ps = connection.prepareStatement(SQL_TOP_1);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return new ProductoDTO(
                        rs.getInt("idProducto"),
                        rs.getString("nombre"),
                        rs.getFloat("recaudacion")
                );
            }
            return null; // no hay datos
        } catch (Exception e) {
            throw new RuntimeException("Error obteniendo producto que más recaudó", e);
        }
    }

    @Override
    public List<ProductoDTO> getAll() throws SQLException {
        List<ProductoDTO> list = new java.util.ArrayList<>();
        String sql = "SELECT * FROM producto ORDER BY nombre";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt(ProductoDTO.ID);
            String nombre = rs.getString(ProductoDTO.NOMBRE);
            float valor = rs.getFloat(ProductoDTO.VALOR);
            ProductoDTO productoDTO = new ProductoDTO(id, nombre, valor);
            list.add(productoDTO);
        }
        return list;
    }
}
