
package elmansajero.data;

import elmensajero.Contact;

/**
 *
 * @author Vinicius
 */
public interface DataListener {
    
    public void contactStatusUpdated(Contact c);
    
    public void messageReceived(Contact c);
    
//    public void messageSent(Message m);
    
}
