
package elmensajero.data.base;

import elmensajero.Contact;
import elmensajero.Message;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Samuel
 */
public class Database {
    
    public static int addContact (ContactDB contact){
        DatabaseAddContact addC = new DatabaseAddContact();
        if ( Database.searchContact(contact.getEmail()) != null ){
            return -1;
        }
        return (addC.addContact(contact, DatabaseConnection.getConnection()) ? 1:0);
    }
    
    public static Contact[] searchAllContact (Contact contact){
        DatabaseSearchAllContact srcAll = new DatabaseSearchAllContact();
        try {
            return srcAll.searchContact(contact, DatabaseConnection.getConnection());
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static ContactDB searchContact (String email){
        DatabaseSearchContact src = new DatabaseSearchContact();
        try {
            return src.searchContact(email, DatabaseConnection.getConnection());
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static ContactDB login(String email, String password){
        ContactDB contact = Database.searchContact(email);
        if ( contact != null ){
            if ( contact.getPassword().equals(password) ){
                return contact;
            }
        }
        return null;
    }
    
    public static boolean addMessage (Message message){
        DatabaseAddMessage addM = new DatabaseAddMessage();
        return (addM.addMessage(message, DatabaseConnection.getConnection()));
    }
    
    public static Message[] searchMessage(Contact c1, Contact c2){
        DatabaseSearchMessage srcM = new DatabaseSearchMessage();
        return srcM.searchMessages(c1, c2, DatabaseConnection.getConnection());
    }
}
