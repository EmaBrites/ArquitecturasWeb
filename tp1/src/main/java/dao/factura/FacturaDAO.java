package dao.factura;

import dto.FacturaDTO;
import entities.Factura;

import java.sql.SQLException;
import java.util.List;

public interface FacturaDAO {
    void insert(Factura factura) throws SQLException;

    void update(Factura factura) throws SQLException;

    void delete(int id) throws SQLException;

    List<FacturaDTO> getAll() throws SQLException;

    FacturaDTO getById(int id) throws SQLException;
}
