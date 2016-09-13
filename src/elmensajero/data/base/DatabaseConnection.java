
package elmensajero.data.base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Samuel
 */
public class DatabaseConnection {

    private static Connection connection = null;
    public static Connection getConnection(){
        if(connection == null)
        try{
           
                connection = DriverManager.getConnection("jdbc:mysql://localhost/elmensajero","root", "");
         
        }catch(SQLException e){
            System.err.println(e);
        }
        
        return connection;
    }

    
}
