package dao.producto;

import dto.ProductoDTO;
import entities.Producto;

import java.sql.SQLException;
import java.util.List;

public interface ProductoDAO {
    void insert(Producto producto) throws SQLException;

    void update(Producto producto) throws SQLException;

    void delete(int id) throws SQLException;

    ProductoDTO getById(int id) throws SQLException;

    List<ProductoDTO> getAll() throws SQLException;

    ProductoDTO findProductoQueMasRecaudo();
}
