package dao.cliente;

import dto.ClienteDTO;
import entities.Cliente;

import java.sql.SQLException;
import java.util.List;

public interface ClienteDAO {
    void insert(Cliente c) throws SQLException;

    List<ClienteDTO> getAll() throws SQLException;

    ClienteDTO getById(int id) throws SQLException;

    void update(Cliente c) throws SQLException;

    void delete(int id) throws SQLException;
}
