
package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.derby.jdbc.ClientDriver;

/**
 *
 * @author Mahmoud
 */
public class DatabaseAccess {
    private final static String USER_NAME = "mahmoud";
    private final static String PASSWORD = "1234";
   
    private final static String URL = "jdbc:derby://localhost:1527/tictactoe";
    
    static Connection connection;
    
    public static void setUpConnection() {
        try {
            DriverManager.registerDriver(new ClientDriver());
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
