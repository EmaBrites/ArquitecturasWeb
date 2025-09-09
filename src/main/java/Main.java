import dao.cliente.ClienteDAO;
import dao.factory.DAOFactory;
import dao.factory.MySqlDAOFactory;
import dao.producto.ProductoDAO;
import dto.ClienteDTO;
import dto.ProductoDTO;
import utils.MySqlHelper;

import java.util.List;

public class Main {
    public static void main(String []args) throws Exception {
        MySqlHelper mySqlHelper = new MySqlHelper();
//        mySqlHelper.dropTables();
//        mySqlHelper.createTables();
//        mySqlHelper.populateDB();

        DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);

        ProductoDAO productoDAO = factory.getProductoDAO();
        ProductoDTO top = productoDAO.findProductoQueMasRecaudo();

        if (top == null) {
            System.out.println("No hay datos de facturación aún.");
        } else {
            System.out.println("Producto que más recaudó:");
            System.out.println(top); // imprime id, nombre y recaudación total
        }


        ClienteDAO clienteDAO = factory.getClienteDAO();
        System.out.println(clienteDAO.getAll());


        System.out.println("=== Clientes ordenados por facturación ===");
        // Llama al método ClienteDAOImpl
        List<ClienteDTO> clientes = clienteDAO.getClientesOrdenadosPorFacturacion();

        for (ClienteDTO c : clientes) {
            System.out.println(c);
        }
    }
}
