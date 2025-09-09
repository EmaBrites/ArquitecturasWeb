import dao.factory.DAOFactory;
import dao.factory.MySqlDAOFactory;
import dao.producto.ProductoDAO;
import dto.ProductoDTO;
import utils.MySqlHelper;

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
    }
}
