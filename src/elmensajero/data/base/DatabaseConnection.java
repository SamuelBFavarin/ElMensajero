
package elmensajero.data.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author Samuel
 */
public class DatabaseConnection {

    private static Connection connection = null;
    public static Connection getConnection(){
        Properties databaseData = new Properties();
        try {
            databaseData.load(new FileInputStream("./databasedata.properties"));
        } catch (IOException ex) {
            System.exit(0);
        }
        if(connection == null)
        try{
            
            connection = DriverManager.getConnection(
                "jdbc:mysql://localhost/"
               +databaseData.getProperty("database"),
                databaseData.getProperty("user"),
                databaseData.getProperty("password")
            );
         
        }catch(SQLException e){
            System.err.println(e);
        }
        
        return connection;
    }

    
}
