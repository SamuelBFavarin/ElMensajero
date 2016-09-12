
package elmensajero.data.socket;

import elmensajero.Contact;
import elmensajero.Message;
import elmensajero.data.DataListener;
import elmensajero.data.RetrieveDataListener;
import elmensajero.data.SocketData;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vinicius
 */
public class Client implements Runnable, RetrieveDataListener{
    
    private static final String HOST = "127.0.0.1";
    private static final int    PORT = 3043;
    private Thread thread;
    private Socket socket;
    
    private Contact contact;
    private final DataListener dataListener;
    
    public Client(DataListener dataListener) {
        this.dataListener = dataListener;
        this.thread = new Thread(this);
        this.socket = null;
    }
    
    public void start(Contact contact){
        if ( this.thread.getState() == Thread.State.TERMINATED ){
            this.thread = new Thread(this);
        }
        this.contact = contact;
        thread.start();
    }
    
    @Override
    public void run(){
        socket = null;
        try {
            socket = new Socket(HOST, PORT);
            SocketData.writeByte(socket, SocketData.ConnectionType.LISTENER);
            SocketData.writeObject(socket, this.contact);
            startServerListener();
        } catch (Exception ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            dataListener.connectionError();
            try {
                socket.close();
            } catch (Exception e) {}
            return;
        }
    }
    
    private void startServerListener(){
        dataListener.connected();
        try {
            byte nextByte;
            while ( true ){
                nextByte = SocketData.nextByte(socket);
                switch ( nextByte ){
                    
                case SocketData.IS_ALIVE:
                    SocketData.writeByte(socket, SocketData.IS_ALIVE);
                    break;
                    
                case DataListener.CONTACT_STATUS_UPDATED:
                    Contact c = (Contact) SocketData.readObject(socket);
                    dataListener.contactStatusUpdated(c);
                    System.out.println("Contato atualizado");
                    break;
                    
                case DataListener.MESSAGE_RECEIVED:
                    Message m = (Message) SocketData.readObject(socket);
                    dataListener.messageReceived(m);
                    System.out.println("Mensagem recebida");
                    break;
                    
                default:
                    System.out.println("Byte não identificado");
                    throw new Exception();
                }
            }
        } catch ( Exception e ){
            dataListener.connectionError();
        }
    }
    
    private Object makeRequest(byte request, Object data) throws Exception {
        Socket requestSocket = new Socket(HOST, PORT);
        SocketData.writeByte(requestSocket, SocketData.ConnectionType.REQUEST);
        SocketData.writeByte(requestSocket, request);
        SocketData.writeObject(requestSocket, data);
        return SocketData.readObject(requestSocket);
    }
    
    @Override
    public Message[] getAllMessages(Contact a, Contact b) {
        try {
            Object data = new Contact[]{ a, b };
            return (Message[]) makeRequest(RetrieveDataListener.ALL_MESSAGES, data);
        } catch (Exception e){
        }
        return null;
    }

    @Override
    public boolean sendMessage(Message message) {
        try {
            byte res = (byte) makeRequest(RetrieveDataListener.SEND_MESSAGE, message);
            return ( res == RetrieveDataListener.MESSAGE_SENT );
        } catch (Exception e){
        }
        return false;
    }
    
    @Override
    public Contact[] getAllContacts(){
        try {
            return (Contact[]) makeRequest(RetrieveDataListener.ALL_CONTACTS, this.contact);
        } catch (Exception e){
        }
        return null;
    }
}
