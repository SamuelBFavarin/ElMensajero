
package elmensajero.data;

import elmensajero.Contact;
import elmensajero.Message;

/**
 *
 * @author Vinicius
 */
public interface DataListener {
    
    /**
     *
     */
    public void connected();
    
    /**
     *
     */
    public void connectionError();
    
    
    public static final byte CONTACT_STATUS_UPDATED = 0x01;
    public static final byte MESSAGE_RECEIVED  = 0x02;
    
    /**
     *
     * @param contact
     */
    public void contactStatusUpdated(Contact contact);
    
    /**
     *
     * @param message
     */
    public void messageReceived(Message message);
    
}
