
package elmensajero;

import elmensajero.gui.ElMensajeroGUI;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

/**
 *
 * @author Vinicius
 */
public class ElMensajero extends Application {

    private Contact userData;
    private final ObservableList<Contact> contacts;
    
    private ElMensajeroGUI gui;
    private LoginGUI login;

    /**
     *
     */
    public ElMensajero() {
        contacts = FXCollections.observableList(new ArrayList<>());
        userData = new Contact(
            "Vinícius",
            "vinicius@rudinei.cnt.br",
            "https://fbcdn-sphotos-g-a.akamaihd.net/hphotos-ak-frc1/v/t1.0-9/10959463_759249350825529_7123328862024803112_n.jpg?oh=8d0fe4f644fd3ee33deafd3840049e62&oe=5846BB67&__gda__=1480345353_1e4d5e27b1e477383421a05b5bffa27c",
            Contact.Status.ONLINE
        );
    }
    
    public final void addContact(Contact c){
        Platform.runLater(() -> {
            contacts.add(c);
            contacts.sort((Contact a, Contact b) -> {
                return a.getName().compareTo(b.getName());
            });
        });
    }
    
    public final void addContacts(Contact cs[]){
        Platform.runLater(() -> {
            contacts.addAll(Arrays.asList(cs));
            contacts.sort((Contact a, Contact b) -> {
                return a.getName().compareTo(b.getName());
            });
        });
    }
    
    @Override
    public void start(Stage stage) {
        
        gui = new ElMensajeroGUI( stage, contacts );
        login = new LoginGUI(stage);
        
        login.show();
        login.setLoginListener((String email, String password) -> {
            if ( email.isEmpty() || password.isEmpty() ){
                System.out.println("Deveria ser tratado");
            }
            System.out.println("Login");
            System.out.println("E-Mail: "+email);
            System.out.println("Senha: "+password);
            
            gui.show();
            
        });
        
        new Thread(() -> {
            gui.setUserData(userData);
            addContact(new Contact("Teste", "teste@gmail.com", "you.png", Contact.Status.ONLINE));
            addContact(new Contact("Fulano", "teste@gmail.com", "you.png", Contact.Status.ONLINE));
            addContact(new Contact("Sicrano", "teste@gmail.com", "you.png", Contact.Status.ONLINE));
        });
        
    }
    
    public static void main(String[] args){
        launch(args);
    }
}
