
package elmensajero.data;

import elmensajero.Contact;
import elmensajero.Message;

/**
 *
 * @author Vinicius
 */
public interface RetrieveDataListener {
    
    public static final byte ALL_MESSAGES = 0x01;
    public static final byte SEND_MESSAGE = 0x02;
    public static final byte MESSAGE_SENT = 0x03;
    public static final byte SEND_MESSAGE_ERROR = 0x04;
    
    public static final byte ALL_CONTACTS = 0x05;
    
    public static final byte SEND_FILE    = 0x06;
    
    public Message[] getAllMessages(Contact a, Contact b);
    
    public boolean sendMessage(Message m);
    
    public Contact[] getAllContacts();
    
}
