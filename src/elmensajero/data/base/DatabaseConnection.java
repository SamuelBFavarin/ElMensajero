
package elmensajero.data.base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rafael
 */
public class DatabaseConnection {

    public Connection getConnection(){
        Connection connection = null;
       
        
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost","root", "");

        }catch(SQLException e){
            System.err.println(e);
        }
        
        return connection;
    }

    
}
