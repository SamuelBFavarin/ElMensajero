package elmensajero.data.socket;

import elmensajero.Contact;
import elmensajero.Message;
import elmensajero.data.DataListener;
import elmensajero.data.RetrieveDataListener;
import elmensajero.data.SocketData;
import elmensajero.data.base.ContactDB;
import elmensajero.data.base.Database;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author Vinicius
 */
public class ServerRequest {

    private final ServerClients clients;
    private final Socket client;

    public ServerRequest(ServerClients clients, Socket client) {
        this.clients = clients;
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
            
            case RetrieveDataListener.SEND_FILE:
                returnP = receiveFile((String) parameter);
                break;
            
            case RetrieveDataListener.NEW_USER:
                System.out.println("Novo usuario");
                returnP = newUser((ContactDB) parameter);
                break;
            
            case RetrieveDataListener.LOGIN:
                System.out.println("Login");
                returnP = login((ContactDB) parameter);
                break;
            
            default:
                System.err.println("Operação não identificada");
        }
        SocketData.writeObject(client, returnP);
        client.close();
    }
    
    private Object getAllContacts(Contact contact) throws Exception {
        Contact[] contacts = Database.searchAllContact(contact);
        for ( Contact c : contacts ){
            if ( clients.getClients().keySet().contains(c) ){
                c.setStatus(Contact.Status.ONLINE);
                continue;
            }
            for ( Contact co : clients.getClients().keySet() ){
                if ( co.equals(c) ){
                    c.setStatus(Contact.Status.ONLINE);
                    break;
                }
            }
        }
        return contacts;
    }
    
    private Object getAllMessages(Contact[] contacts) throws Exception{
        Contact a = contacts[0];
        Contact b = contacts[1];
        return Database.searchMessage(a, b);
    }
    
    private Object sendMessage(final Message message) throws Exception {
        if ( !Database.addMessage(message) )
        {
            return RetrieveDataListener.SEND_MESSAGE_ERROR;
        }
        Socket contact = clients.getClients().get(message.getReceptor());
        if ( contact == null ){
            for ( Contact co : clients.getClients().keySet() ){
                if ( co.equals(message.getReceptor()) ){
                    contact = clients.getClients().get(co);
                    break;
                }
            }
            if ( contact == null ){
                return RetrieveDataListener.MESSAGE_SENT;
            }
        }
        final Socket friend = contact;
        new Thread(() -> {
            while ( clients.getBlockedClients().contains(friend) ){}
            clients.getBlockedClients().add(friend);
                try {
                    SocketData.writeByte(friend, DataListener.MESSAGE_RECEIVED);
                    SocketData.writeObject(friend, message);
                } catch (Exception e){
                    e.printStackTrace();
                }
            clients.getBlockedClients().remove(friend);
        }).start();
        return RetrieveDataListener.MESSAGE_SENT;
    }
    
    private Object receiveFile(String filename) throws Exception {
        
        String ext = filename.substring( filename.lastIndexOf('.') );
        filename = System.currentTimeMillis() + ext;

        System.out.println("Receiving file");
        DataInputStream in = new DataInputStream( client.getInputStream() );

        SocketData.writeByte(client, SocketData.READY_TO_RECEIVE);
        OutputStream out = new BufferedOutputStream(new FileOutputStream("../http/img/" + filename));
        int aByte;
        do{
            aByte = in.readInt();
            if ( aByte != -1 ){
                out.write(aByte);
            }
        } while ( aByte != -1 );
        System.out.println("Finished");
        SocketData.writeByte(client, SocketData.READY_TO_RECEIVE);

        out.close();

        SocketData.writeObject( client, filename );
        return false;
    }
    
    private Object newUser(ContactDB contact){
        return Database.addContact(contact);
    }
    
    private Object login(ContactDB contact){
        ContactDB c = Database.login(contact.getEmail(), contact.getPassword());
        return c;
    }
    
}
