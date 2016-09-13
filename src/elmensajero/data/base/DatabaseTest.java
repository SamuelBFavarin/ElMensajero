
package elmensajero.data.base;

import elmensajero.Message;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Samuel
 */
public class DatabaseTest {

    public DatabaseTest() {
        Connection connection = DatabaseConnection.getConnection();
        ContactDB contact1 = new ContactDB("Samuel","samuelbfavarin@hotmail.com","fucsdsda","samuel.jpg");
        ContactDB contact2 = new ContactDB("Vinicius","vini@hotmail.com","fucs","vini.jpg");
        Message message1 = new Message(contact1,contact2,"FALA MALUKO",Calendar.getInstance().getTime());
        Message message2 = new Message(contact1,contact2,"TA AI ?",Calendar.getInstance().getTime());
        Message message3 = new Message(contact2,contact1,"CUZÃO",Calendar.getInstance().getTime());
        //DatabaseAddMessage m = new DatabaseAddMessage();
        //m.addMessage(message1, connection);
        //m.addMessage(message2, connection);
        //m.addMessage(message3, connection);
        //DatabaseAddContact add = new DatabaseAddContact();
        //add.addContact(contact, connection);
       // DatabaseSearchContact src = new DatabaseSearchContact();
        //DatabaseSearchAllContact src = new DatabaseSearchAllContact();
        
        DatabaseSearchMessage m = new DatabaseSearchMessage();
        m.searchMessages(contact1, contact2, connection);
        try {  
            //src.searchContact(contact, connection);
            System.out.println("OPEN CONNECTION");
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
 
}
