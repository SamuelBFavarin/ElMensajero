
package elmensajero.data.base;

import elmensajero.Contact;
import elmensajero.Message;
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
public class DatabaseSearchMessage {

    public Message[] searchMessages(Contact c1, Contact c2, Connection connection){
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM messages WHERE (sender = ? AND receptor = ?)"
                            + " OR (receptor = ? AND sender = ?)"
            );
            stmt.setString(1, c1.getEmail());
            stmt.setString(2, c2.getEmail());
            stmt.setString(3, c1.getEmail());
            stmt.setString(4, c2.getEmail());
            ResultSet res = stmt.executeQuery();   
            res.last();
            Message[] m = new Message[res.getRow()];
            int i=0;
            res.beforeFirst();
            while(res.next()){
                DatabaseSearchContact s = new DatabaseSearchContact();
                Contact sender = s.searchContact(res.getString("sender"),connection);
                DatabaseSearchContact r= new DatabaseSearchContact();
                Contact receptor = r.searchContact(res.getString("receptor"), connection);
                
                m[i] = new Message(sender, receptor, res.getString("message"),res.getDate("date"));
                i++;
                
                System.out.println(res.getString("message"));
             }
            
            return m;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseSearchMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
