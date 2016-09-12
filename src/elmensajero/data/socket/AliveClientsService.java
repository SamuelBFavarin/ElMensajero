
package elmensajero.data.socket;

import elmensajero.Contact;
import elmensajero.data.SocketData;
import java.net.Socket;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vinicius
 */
public class AliveClientsService implements Runnable {

    private final Map<Contact,Socket> clients;
    private final Set<Socket> blockedClients;

    private Thread thread;
    
    public AliveClientsService(Map<Contact, Socket> clients, Set<Socket> blockedClients) {
        this.clients = clients;
        this.blockedClients = blockedClients;
        thread = new Thread(this, "Alive clients service");
    }

    public void start(){
        if ( thread.isInterrupted() ){
            thread = new Thread(this, "Alive clients service");
        }
        thread.start();
    }
    
    private void testClient(Contact contact){
        Socket client = clients.get(contact);
        if ( blockedClients.contains( client ) )
            return;
        blockedClients.add(client);
        
        if (!SocketData.testConnection(client)){
            
            System.out.println("Contato sem resposta removido: "+contact.getName());
            clients.remove(contact);
            
        }
        
        blockedClients.remove(client);
    }
    
    private void testClients(){
        Set<Contact> contacts = new HashSet<>(clients.keySet());
        for ( Contact c : contacts ) {
            testClient(c);
        }
    }
    
    @Override
    public void run() {
        while ( true ) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                Logger.getLogger(AliveClientsService.class.getName()).log(Level.WARNING, null, ex);
            }
            testClients();
        }
    }
    
    
    
}
