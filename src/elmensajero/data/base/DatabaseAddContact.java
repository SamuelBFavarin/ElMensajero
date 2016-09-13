
package elmensajero.data.base;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Samuel
 */
public class DatabaseAddContact {
   
    public boolean addContact(ContactDB contact, Connection connection){
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO users(name,password,email,image)"
                            + " VALUES (?,?,?,?)"
            );
            stmt.setString(1, contact.getName());
            stmt.setString(2, contact.getPassword());
            stmt.setString(3, contact.getEmail());
            stmt.setString(4, contact.getImage());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseAddContact.class.getName()).log(Level.SEVERE, null, ex);
        }  
        return false;
    }
    
}
