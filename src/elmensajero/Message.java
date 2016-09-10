
package elmensajero;

import java.util.Date;

/**
 *
 * @author Samuel
 */
public class Message {
    private final Contact sender;
    private final Contact receptor;
    private final String message;
    private final Date date;

    public Message(Contact sender, Contact receptor, String message, Date date) {
        this.sender = sender;
        this.receptor = receptor;
        this.message = message;
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public Contact getReceptor() {
        return receptor;
    }

    public Contact getSender() {
        return sender;
    }

    public Date getDate() {
        return date;
    }
    
  
}
