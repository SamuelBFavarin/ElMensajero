
package elmensajero.data.socket;

import elmensajero.Contact;
import elmensajero.Message;
import elmensajero.data.DataListener;
import elmensajero.data.RetrieveDataListener;
import elmensajero.data.SocketData;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
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
    
    public Server() throws IOException {
        super(PORT);
        clients = FXCollections.observableHashMap();
        System.out.println( "Servidor socket instanciado" );
    }
    
    private void startServerListener(Socket client) throws Exception{
        System.out.println("Iniciado canal de escuta de dados");
        Contact contact = (Contact) SocketData.readObject(client);
        clients.put(contact, client);
    }
    
    private void startRequest(Socket client) throws Exception{
        System.out.println("Iniciada requisição de dados");
        switch ( SocketData.nextByte(client) ){
            
            case RetrieveDataListener.ALL_CONTACTS:
                Contact c = (Contact) SocketData.readObject(client);
                SocketData.writeObject(client, new Contact[]{
                    new Contact("Megan Fox", "teste@gmail.com", "http://sizlingpeople.com/wp-content/uploads/2016/05/Megan-Fox-Instagram.jpg", Contact.Status.ONLINE),
                    new Contact("Teste 1", "teste@gmail.com", "you.png", Contact.Status.OFFLINE),
                    new Contact("Teste 2", "teste@gmail.com", "you.png", Contact.Status.ONLINE),
                    new Contact("Teste 3", "teste@gmail.com", "you.png", Contact.Status.OFFLINE),
                    new Contact("Teste 4", "teste@gmail.com", "you.png", Contact.Status.ONLINE),
                    new Contact("Teste", "teste@gmail.com", "you.png", Contact.Status.OFFLINE),
                    new Contact("Fulano", "teste@gmail.com", "you.png", Contact.Status.OFFLINE),
                    new Contact("Sicrano", "teste@gmail.com", "you.png", Contact.Status.ONLINE)
                });
                
                break;
                
            case RetrieveDataListener.ALL_MESSAGES:
                Contact[] contacts = (Contact[]) SocketData.readObject(client);
                Contact a = contacts[0];
                Contact b = contacts[1];
                SocketData.writeObject(client, new Message[]{
                    new Message(a, b, "Teste 1", new Date()),
                    new Message(b, a, "Teste 2", new Date()),
                    new Message(a, b, "Teste 3", new Date()),
                    new Message(b, a, "Teste 4", new Date())
                });
                
                break;
            case RetrieveDataListener.SEND_MESSAGE:
                Message message = (Message) SocketData.readObject(client);
                Socket friend = clients.get(message.getReceptor());
                SocketData.writeByte(friend, DataListener.MESSAGE_RECEIVED);
                SocketData.writeObject(client, message);
                break;
            default:
                System.out.println("Operação não identificada");
        }
    }
    
    public void start(){
        System.out.println( "Servidor iniciado na porta "+PORT );
        while (true){
            try {
                final Socket client = this.accept();
                System.out.println( "Cliente conectado em IP "+ client.getInetAddress().getHostAddress() );
                new Thread(() -> {
                    try {
                        int connectionType = client.getInputStream().read();
                        if ( connectionType == -1 ){
                            System.err.println("Erro ao ler tipo de conexão");
                            return;
                        }
                        switch ( (byte) connectionType ){
                            
                        case SocketData.ConnectionType.LISTENER:
                            startServerListener(client);
                            break;
                            
                        case SocketData.ConnectionType.REQUEST:
                            startRequest(client);
                            break;
                            
                        default:
                            System.out.println("Tipo de conexão não reconhecido");
                        }
                    } catch (Exception ex){
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                        try {
                            client.close();
                        } catch (Exception e) {}
                    }
                }).start();
                
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
