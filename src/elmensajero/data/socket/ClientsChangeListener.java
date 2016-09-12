
package elmensajero.data.socket;

import elmensajero.Contact;
import elmensajero.data.DataListener;
import elmensajero.data.SocketData;
import java.net.Socket;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.MapChangeListener;

/**
 *
 * @author Vinicius
 */
public class ClientsChangeListener implements MapChangeListener<Contact,Socket>, Runnable {
    
    private final Map<Contact,Socket> clients;
    private final Set<Socket> blockedClients;

    private MapChangeListener.Change<? extends Contact, ? extends Socket> lastChange;
    
    public ClientsChangeListener(Map<Contact, Socket> clients, Set<Socket> blockedClients) {
        this.clients = clients;
        this.blockedClients = blockedClients;
    }
    
    @Override
    public void onChanged(MapChangeListener.Change<? extends Contact, ? extends Socket> change) {
        lastChange = change;
        new Thread( this, "Sending user updates" ).start();
    }

    @Override
    public void run() {
        Contact contact = lastChange.getKey();
        if ( lastChange.wasAdded() ){
            addedContact( contact, clients.keySet() );
        } else if ( lastChange.wasRemoved() ){
            removedContact( contact, clients.values() );
        }
    }
    
    private void addedContact(Contact contact, Set<Contact> contacts){
        for ( Contact cont : contacts ){
            if ( !contact.equals( cont ) ){
                Socket client = clients.get( cont );
                while ( blockedClients.contains( cont ) ){}
                blockedClients.add(client);
                try {
                    SocketData.writeByte(client, DataListener.CONTACT_STATUS_UPDATED);
                    SocketData.writeObject(client, contact);
                } catch (Exception ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
                blockedClients.remove(client);
            }
        }
    }
    
    private void removedContact(Contact contact, Collection<Socket> clients){
        contact.setStatus(Contact.Status.OFFLINE);
        for ( Socket client : clients ){
            while ( blockedClients.contains(client) ){}
            blockedClients.add(client);
            try {
                SocketData.writeByte(client, DataListener.CONTACT_STATUS_UPDATED);
                SocketData.writeObject(client, contact);
            } catch (Exception ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
            blockedClients.remove(client);
        }
    }
    
}
