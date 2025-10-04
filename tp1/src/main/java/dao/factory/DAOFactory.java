package dao.factory;

import dao.cliente.ClienteDAO;
import java.sql.SQLException;
import dao.producto.ProductoDAO;

public abstract class DAOFactory {
    public static final int MYSQL = 1;
//    public static final int DERBY = 2;

    public abstract ClienteDAO getClienteDAO() throws SQLException;

    public static DAOFactory getDAOFactory(int whichFactory) throws SQLException {
        switch (whichFactory) {
            case MYSQL:
                return MySqlDAOFactory.getInstance();
//            case DERBY:
//                return DerbyDAOFactory.getInstance();
            default:
                return null;
        }
    }
    public abstract ProductoDAO getProductoDAO() throws SQLException;
}
