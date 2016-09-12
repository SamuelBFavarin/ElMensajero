
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
   
    public void addContact(ContactsDB contact, Connection connection){
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO users(name,password,email) VALUES ("+contact.getName()+","+contact.getPassword()+","+contact.getEmail()+")");
            stmt.executeQuery();
            System.out.println("INSERT DATA");
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseAddContact.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
}
