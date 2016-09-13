
package elmensajero.data.base;

import elmensajero.Contact;
import elmensajero.Message;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Samuel
 */
public class Database {
    
    public int addContact (ContactDB contact, Connection connection){
        DatabaseAddContact addC = new DatabaseAddContact();
        if ( searchContact(contact.getEmail(), connection) != null ){
            return -1;
        }
        return (addC.addContact(contact, connection) ? 1:0);
    }
    
    public Contact[] searchAllContact (ContactDB contact, Connection connection){
        DatabaseSearchAllContact srcAll = new DatabaseSearchAllContact();
        try {
            return srcAll.searchContact(contact, connection);           
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public Contact searchContact (String email, Connection connection){
        DatabaseSearchContact src = new DatabaseSearchContact();
        try {
            return src.searchContact(email, connection);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public boolean addMessage (Message message, Connection connection){
        DatabaseAddMessage addM = new DatabaseAddMessage();
        return (addM.addMessage(message, connection));
    }
    
    public Message[] searchMessage(Contact c1, Contact c2, Connection connection){
        DatabaseSearchMessage srcM = new DatabaseSearchMessage();
        return srcM.searchMessages(c1, c2, connection);
    }
}
