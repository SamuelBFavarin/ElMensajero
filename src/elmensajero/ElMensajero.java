
package elmensajero;

import elmensajero.gui.LoginGUI;
import elmensajero.data.DataListener;
import elmensajero.data.UserDataProperties;
import elmensajero.data.socket.Client;
import elmensajero.gui.ElMensajeroGUI;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Vinicius
 */
public class ElMensajero extends Application {
    
    private final ObservableList<Contact> contacts;
    
    private ElMensajeroGUI gui;
    private LoginGUI login;

    private final Client socketClient;
    
    /**
     *
     */
    public ElMensajero() {
        socketClient = new Client(new SocketDataListener());
        contacts = FXCollections.observableList(new ArrayList<>());
    }
    
    public class SocketDataListener implements DataListener{
        @Override
        public void connected() {
            new Thread(() -> {
                setContacts( socketClient.getAllContacts() );
            }).start();
        }
        @Override
        public void connectionError() {
            gui.showError("Erro de conexão com o servidor");
        }
        @Override
        public void contactStatusUpdated(Contact contact) {
            new Thread(() -> {
                int index = -1;
                for (int i = 0; i < contacts.size(); i++) {
                    if ( contacts.get(i).equals( contact ) ){
                        index = i;
                        break;
                    }
                }
                if ( index == -1 ){
                    addContact(contact);
                } else {
                    contacts.set(index, contact);
                }
            }).start();
        }
        @Override
        public void messageReceived(Message m) {
            new Thread(() -> {
                gui.addMessage(m);
            }).start();
        }
        
    }
    
    public final void addContact(Contact c){
        new Thread(() -> {
            for (int i = 0; i < contacts.size(); i++) {
                if ( contacts.get(i).equals( c ) ){
                    contacts.remove(i);
                    break;
                }
            }
            contacts.add(c);
            contacts.sort( Contact.ContactComparator.getInstance() );
        }).start();
    }
    
    public final void setContacts(Contact cs[]){
        new Thread(() -> {
            contacts.setAll(Arrays.asList(cs));
            contacts.sort( Contact.ContactComparator.getInstance() );
        }).start();
    }
    
    
    
    private class LoginGUIListener implements LoginGUI.LoginListener{

        @Override
        public void tryLogin(String email, String password) {
            if ( email.isEmpty() || password.isEmpty() ){
                login.showError("Preencha todos os campos");
                return;
            }
            Contact user = new Contact(email, "Vinicius", "https://fbcdn-sphotos-g-a.akamaihd.net/hphotos-ak-frc1/v/t1.0-9/10959463_759249350825529_7123328862024803112_n.jpg?oh=8d0fe4f644fd3ee33deafd3840049e62&oe=5846BB67&__gda__=1480345353_1e4d5e27b1e477383421a05b5bffa27c", Contact.Status.ONLINE);
            UserDataProperties.setUserData( user );
            gui.setUserData( user );
            
            gui.show();
            
            socketClient.start( user );
        }
        
    }
    
    @Override
    public void start(Stage stage) {
        
        gui = new ElMensajeroGUI( stage, contacts, socketClient );
        login = new LoginGUI(stage);
        login.setLoginListener(new LoginGUIListener());
        
        new Thread(() -> {
            Contact user =  UserDataProperties.getUserData();
            if ( user == null ){
                login.show();
            } else {
                gui.show();
                gui.setUserData( user );

                socketClient.start( user );
            }
            
        }).start();
        
        stage.setOnCloseRequest((WindowEvent t) -> {
            Platform.exit();
            System.exit(0);
        });
    }
    
    public static void main(String[] args){
        launch(args);
    }
    
}
