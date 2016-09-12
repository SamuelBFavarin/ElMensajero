
package elmensajero.data.base;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rafael
 */
public class DatabaseTest {

    public DatabaseTest() {
        Connection connection = new DatabaseConnection().getConnection();
        ContactsDB contact = new ContactsDB("Samuel","samuelbfavarin@hotmail.com","fuckthis");
        DatabaseAddContact add = new DatabaseAddContact();
        add.addContact(contact, connection);
        System.out.println("OPEN CONNECTION");
        try {   
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
 
}
