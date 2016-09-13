
package elmensajero.data.base;

import elmensajero.Contact;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Samuel
 */
public class DatabaseSearchAllContact {

     public Contact[] searchContact(ContactDB contact, Connection connection) throws SQLException{
         Statement stmt = connection.createStatement();
         ResultSet res = stmt.executeQuery("SELECT * FROM users");
         res.last();
         Contact[] c = new Contact[res.getRow()];
         int i=0;
         res.beforeFirst();
         while(res.next()){
             c[i] = new ContactDB(
                     res.getString("name"),
                     res.getString("email"), 
                     res.getString("password"),
                     res.getString("image")
             );
             i++;
         }
       return c;    
     }
     
     public void printContacts(Contact[] c){
         for (Contact contact : c) {
             System.out.println(contact.getName());
         }
     }
}
