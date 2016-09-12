
package elmensajero.data.socket;

import elmensajero.Contact;
import elmensajero.data.SocketData;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

/**
 *
 * @author Vinicius
 */
public class Server extends ServerSocket {
    
    private static final int PORT = 3043;
    private final ObservableMap<Contact,Socket> clients;
    private final Set<Socket> blockedClients;
    
    public Server() throws IOException {
        super(PORT);
        blockedClients = new HashSet<>();
        clients = FXCollections.observableHashMap();
        clients.addListener(new ClientsChangeListener(clients, blockedClients));
        System.out.println( "Servidor socket instanciado" );
    }
    
    private void startServerListener(Socket client) throws Exception{
        System.out.println("Iniciado canal de escuta de dados");
        Contact contact = (Contact) SocketData.readObject(client);
        clients.put(contact, client);
    }
    
    private void newClient(Socket client){
        try {
            switch ( SocketData.nextByte(client) ){

                case SocketData.ConnectionType.LISTENER:
                    startServerListener(client);
                    break;

                case SocketData.ConnectionType.REQUEST:
                    (new ServerRequest(clients,blockedClients,client)).start();
                    break;

                default:
                    System.out.println("Tipo de conexão não reconhecido");
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void start(){
        System.out.println( "Servidor iniciado na porta "+PORT );
        (new AliveClientsService(clients, blockedClients)).start();
        while (true){
            try {
                final Socket client = this.accept();
                System.out.println( "Cliente conectado em IP "+ client.getInetAddress().getHostAddress() );
                new Thread(() -> {
                    
                    newClient(client);
                    
                }, "Client serve Thread").start();
                
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void main(String[] args){
        try {
            (new Server()).start();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
