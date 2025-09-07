import dao.factory.MySqlDAOFactory;
import utils.MySqlHelper;

public class Main {
    public static void main(String []args) throws Exception {
        MySqlHelper mySqlHelper = new MySqlHelper();
        mySqlHelper.dropTables();
        mySqlHelper.createTables();
        mySqlHelper.populateDB();
    }
}
