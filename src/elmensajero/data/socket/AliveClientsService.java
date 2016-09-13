
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

    private final ServerClients clients;

    private Thread thread;
    
    public AliveClientsService(ServerClients clients) {
        this.clients = clients;
        thread = new Thread(this, "Alive clients service");
    }

    public void start(){
        if ( thread.isInterrupted() ){
            thread = new Thread(this, "Alive clients service");
        }
        thread.start();
    }
    
    private void testClient(Contact contact){
        Socket client = clients.getClients().get(contact);
        if ( clients.getBlockedClients().contains( client ) )
            return;
        clients.getBlockedClients().add(client);
        
        if (!SocketData.testConnection(client)){
            
            System.out.println("Contato sem resposta removido: "+contact.getName());
            clients.removeContact(contact);
            
        }
        
        clients.getBlockedClients().remove(client);
    }
    
    private void testClients(){
        Set<Contact> contacts = new HashSet<>(clients.getClients().keySet());
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
