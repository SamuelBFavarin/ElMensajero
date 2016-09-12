
package elmensajero.data.socket;

import elmensajero.Contact;
import elmensajero.Message;
import elmensajero.data.DataListener;
import elmensajero.data.RetrieveDataListener;
import elmensajero.data.SocketData;
import java.net.Socket;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Vinicius
 */
public class ServerRequest {

    private final Map<Contact,Socket> clients;
    private final Set<Socket> blockedClients;
    private final Socket client;

    public ServerRequest(Map<Contact,Socket> clients, Set<Socket> blockedClients, Socket client) {
        this.clients = clients;
        this.blockedClients = blockedClients;
        this.client = client;
    }
    
    public void start() throws Exception{
        System.out.println("Iniciada requisição de dados");
        
        byte nextByte = SocketData.nextByte(client);
        
        Object parameter = SocketData.readObject(client);
        Object returnP = null;
        
        switch ( nextByte ){
            
            case RetrieveDataListener.ALL_CONTACTS:
                returnP = getAllContacts((Contact) parameter);
                break;
                
            case RetrieveDataListener.ALL_MESSAGES:
                returnP = getAllMessages((Contact[]) parameter);
                break;
                
            case RetrieveDataListener.SEND_MESSAGE:
                returnP = sendMessage((Message) parameter);
                break;
                
            default:
                System.err.println("Operação não identificada");
        }
        SocketData.writeObject(client, returnP);
        client.close();
    }
    
    private Object getAllContacts(Contact c) throws Exception {
        Set<Contact> contSet = new HashSet<>( clients.keySet() );
        for ( Contact co : contSet ){
            if ( co.equals(c) ){
                contSet.remove(co);
                break;
            }
        }
        Contact[] contacts = new Contact[contSet.size()];
        contSet.toArray(contacts);
        return contacts;
    }
    
    private Object getAllMessages(Contact[] contacts) throws Exception{
        Contact a = contacts[0];
        Contact b = contacts[1];
        return new Message[]{
            new Message(a, b, "Teste 1", new Date()),
            new Message(b, a, "Teste 2", new Date()),
            new Message(a, b, "Teste 3", new Date()),
            new Message(b, a, "Teste 4", new Date())
        };
    }
    
    private Object sendMessage(Message message) throws Exception {
        Socket friend = clients.get(message.getReceptor());
        if ( friend == null ){
            for ( Contact co : clients.keySet() ){
                if ( co.equals(message.getReceptor()) ){
                    friend = clients.get(co);
                    break;
                }
            }
            if ( friend == null ){
                return RetrieveDataListener.SEND_MESSAGE_ERROR;
            }
        }
        while ( blockedClients.contains(friend) ){}
        blockedClients.add(friend);
        
        SocketData.writeByte(friend, DataListener.MESSAGE_RECEIVED);
        SocketData.writeObject(friend, message);
        
        blockedClients.remove(friend);
        return RetrieveDataListener.MESSAGE_SENT;
    }
    
}
