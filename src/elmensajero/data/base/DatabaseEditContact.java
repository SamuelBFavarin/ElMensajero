package elmensajero.data.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Samuel
 */
public class DatabaseEditContact {
   
    public boolean editContact(ContactDB contact, Connection connection){
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE users SET "
                            + "name = ?,"
                            + "password = ?,"
                            + "image = ? "
                            + "WHERE email = ?"
            );
            stmt.setString(1, contact.getName());
            stmt.setString(2, contact.getPassword());
            stmt.setString(3, contact.getImage());
            stmt.setString(4, contact.getEmail());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseAddContact.class.getName()).log(Level.SEVERE, null, ex);
        }  
        return false;
    }
    
}
