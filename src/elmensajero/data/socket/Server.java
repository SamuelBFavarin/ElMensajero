package elmensajero.data.socket;

import elmensajero.Contact;
import elmensajero.data.SocketData;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vinicius
 */
public class Server extends ServerSocket {
    
    private final ServerClients clients;
    
    public Server() throws IOException {
        super(SocketData.PORT);
        clients = new ServerClients();
        System.out.println( "Servidor socket instanciado" );
    }
    
    private void startServerListener(Socket client) throws Exception{
        System.out.println("Iniciado canal de escuta de dados");
        Contact contact = (Contact) SocketData.readObject(client);
        clients.addContact(contact, client);
    }
    
    private void newClient(Socket client){
        try {
            switch ( SocketData.nextByte(client) ){

                case SocketData.ConnectionType.LISTENER:
                    startServerListener(client);
                    break;

                case SocketData.ConnectionType.REQUEST:
                    (new ServerRequest(clients,client)).start();
                    break;

                default:
                    System.out.println("Tipo de conexão não reconhecido");
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void start(){
        System.out.println( "Servidor iniciado na porta " + SocketData.PORT );
        (new AliveClientsService(clients)).start();
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
