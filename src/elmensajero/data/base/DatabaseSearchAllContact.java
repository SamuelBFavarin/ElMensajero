
package elmensajero.data.base;

import elmensajero.Contact;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Samuel
 */
public class DatabaseSearchAllContact {

     public Contact[] searchContact(Contact contact, Connection connection) throws SQLException{
         PreparedStatement stmt = connection.prepareCall("SELECT * FROM users WHERE email != ?");
         stmt.setString(1, contact.getEmail());
         ResultSet res = stmt.executeQuery();
         res.last();
         Contact[] c = new Contact[res.getRow()];
         int i = 0;
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
