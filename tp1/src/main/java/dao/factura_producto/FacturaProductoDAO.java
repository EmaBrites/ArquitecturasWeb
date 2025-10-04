package dao.factura_producto;

import dto.FacturaProductoDTO;
import entities.FacturaProducto;

import java.sql.SQLException;
import java.util.List;

public interface FacturaProductoDAO {
    void insert(FacturaProducto facturaProducto) throws SQLException;

    void delete(int id) throws SQLException;

    void update(FacturaProducto facturaProducto) throws SQLException;

    FacturaProductoDTO getById(int idFactura, int idProducto) throws SQLException;

    List<FacturaProductoDTO> getAll() throws SQLException;
}
