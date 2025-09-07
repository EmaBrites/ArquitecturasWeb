package dao.factory;

import com.mysql.cj.jdbc.Driver;
import dao.cliente.ClienteDAO;
import dao.cliente.ClienteDAOImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class MySqlDAOFactory extends DAOFactory {

    public static final String URI = "jdbc:mysql://localhost:3306/tpe1";
    public static MySqlDAOFactory instance;
    public static Connection conn;
    public static Driver driver;

    private MySqlDAOFactory() throws SQLException {
        driver = new Driver();
    }

    public static MySqlDAOFactory getInstance() throws SQLException {
        if (instance == null) {
            instance = new MySqlDAOFactory();
        }
        return instance;
    }

    public static Connection getConn() throws SQLException {
        Properties properties = new Properties();
        properties.put("user","root");
        properties.put("password","password");
        getInstance();
        if (conn == null){
            conn= driver.connect(URI,properties);
            conn.setAutoCommit(false);
        }
        return conn;
    }

    public ClienteDAO getClienteDAO() throws SQLException {
        return ClienteDAOImpl.getInstance();
    }
}
