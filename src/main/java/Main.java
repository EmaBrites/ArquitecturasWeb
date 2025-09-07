import dao.cliente.ClienteDAO;
import dao.factory.DAOFactory;
import dao.factory.MySqlDAOFactory;
import utils.MySqlHelper;

public class Main {
    public static void main(String []args) throws Exception {
        MySqlHelper mySqlHelper = new MySqlHelper();
        //mySqlHelper.dropTables();
        //mySqlHelper.createTables();
        //mySqlHelper.populateDB();
        DAOFactory factory = MySqlDAOFactory.getInstance();
        ClienteDAO clienteDAO = factory.getClienteDAO();
        System.out.println(clienteDAO.getAll());
    }
}
