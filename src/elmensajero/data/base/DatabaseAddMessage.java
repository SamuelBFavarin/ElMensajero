
package elmensajero.data.base;

import elmensajero.Message;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Samuel
 */
public class DatabaseAddMessage {

    public boolean addMessage(Message message, Connection connection){
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO messages(sender,receptor,date,message)"
                            + " VALUES (?,?,NOW(),?)"
            );
            stmt.setString(1, message.getSender().getEmail());
            stmt.setString(2, message.getReceptor().getEmail());
            stmt.setString(3, message.getMessage());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseAddMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
