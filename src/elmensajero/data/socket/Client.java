package elmensajero.data.socket;

import elmensajero.Contact;
import elmensajero.Message;
import elmensajero.data.DataListener;
import elmensajero.data.RetrieveDataListener;
import elmensajero.data.SocketData;
import elmensajero.data.base.ContactDB;
import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.ProgressIndicator;

/**
 *
 * @author Vinicius
 */
public class Client implements Runnable, RetrieveDataListener{
    
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
    
    public void close(){
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run(){
        socket = null;
        try {
            socket = new Socket(SocketData.HOST, SocketData.PORT);
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
        Socket requestSocket = new Socket(SocketData.HOST, SocketData.PORT);
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
        if ( message.getReceptor() == null ){
            return false;
        }
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
    
    @Override
    public String sendFile(File file, final ProgressIndicator progress){
        try {
            Socket requestSocket = new Socket(SocketData.HOST, SocketData.PORT);
            SocketData.writeByte(requestSocket, SocketData.ConnectionType.REQUEST);
            SocketData.writeByte(requestSocket, RetrieveDataListener.SEND_FILE);
            
            InputStream in = new BufferedInputStream(new FileInputStream(file));
            DataOutputStream out = new DataOutputStream( requestSocket.getOutputStream() );
            
            SocketData.writeObject(requestSocket, file.getName());

            if ( SocketData.nextByte(requestSocket) != SocketData.READY_TO_RECEIVE ){
                return null;
            }
            
            double total = file.length();
            double atual = 0;
            int aByte;
            do{
                aByte = in.read();
                out.writeInt(aByte);
                final double step = atual++;
                Platform.runLater(() -> {
                    progress.setProgress(step/total);
                });
            } while ( aByte != -1 );
            
            in.close();
            
            if ( SocketData.nextByte(requestSocket) != SocketData.READY_TO_RECEIVE ){
                return null;
            }
            return (String) SocketData.readObject(requestSocket);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public int newUser(ContactDB contact){
        try {
            return (int) makeRequest(RetrieveDataListener.NEW_USER, contact);
        } catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }
    
    @Override
    public boolean editUser(ContactDB contact){
        try {
            return (boolean) makeRequest(RetrieveDataListener.EDIT_USER, contact);
        } catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    
    @Override
    public ContactDB login(ContactDB contact){
        try {
            return (ContactDB) makeRequest(RetrieveDataListener.LOGIN, contact);
        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
}
