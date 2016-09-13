
package elmensajero.data.base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Samuel
 */
public class DatabaseConnection {

    private static Connection connection = null;
    public static Connection getConnection(){
        if(connection == null)
        try{
           
                connection = DriverManager.getConnection("jdbc:mysql://localhost/elmensajero", "root", "Vd23031996");
         
        }catch(SQLException e){
            System.err.println(e);
        }
        
        return connection;
    }

    
}
