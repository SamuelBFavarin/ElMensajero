
package elmensajero.data.socket;

import elmensajero.Contact;
import elmensajero.data.DataListener;
import elmensajero.data.SocketData;
import java.net.Socket;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vinicius
 */
public class ServerClients {
    
    private final Map<Contact,Socket> clients;
    private final Set<Socket> blockedClients;
    
    public ServerClients() {
        this.clients = new HashMap<>();
        this.blockedClients = new HashSet<>();
    }
    
    public Map<Contact,Socket> getClients(){
        return clients;
    }
    
    public Set<Socket> getBlockedClients(){
        return blockedClients;
    }
    
    public void addContact(Contact contact,Socket socket){
        clients.put(contact, socket);
        for ( Contact cont : clients.keySet() ){
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
    
    public void removeContact(Contact contact){
        contact.setStatus(Contact.Status.OFFLINE);
        for ( Socket client : clients.values() ){
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
        clients.remove(contact);
    }
    
}
