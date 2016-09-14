
package elmensajero.data;

import elmensajero.Contact;
import elmensajero.Message;
import elmensajero.data.base.ContactDB;
import java.io.File;
import javafx.scene.control.ProgressIndicator;

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
    public static final byte NEW_USER     = 0x06;
    public static final byte EDIT_USER     = 0x07;
    public static final byte LOGIN        = 0x08;
    
    public static final byte SEND_FILE    = 0x09;
    
    public Message[] getAllMessages(Contact a, Contact b);
    
    public boolean sendMessage(Message m);
    
    public Contact[] getAllContacts();
    
    public String sendFile(File file, final ProgressIndicator progress);
    
    public ContactDB login(ContactDB contact);
    
    public int newUser(ContactDB contact);
    
    public boolean editUser(ContactDB contact);
    
}
