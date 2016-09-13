package elmensajero.data.base;

import elmensajero.Contact;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Samuel
 */
public class DatabaseSearchContact {
    
    public Contact searchContact(String email, Connection connection) throws SQLException{
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM users WHERE email = ?"
            );
            stmt.setString(1, email);
            ResultSet res = stmt.executeQuery();
            if(res.first()== false) return null;
            
            //System.out.println(res.getString("email"));
            return new ContactDB(
                res.getString("name"),
                res.getString("email"),
                res.getString("password"),
                res.getString("image")
            );
          
        }catch (SQLException ex) {
            Logger.getLogger(DatabaseSearchContact.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}   

