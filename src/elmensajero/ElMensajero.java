package elmensajero;

import elmensajero.gui.LoginGUI;
import elmensajero.data.DataListener;
import elmensajero.data.UserDataProperties;
import elmensajero.data.base.ContactDB;
import elmensajero.data.socket.Client;
import elmensajero.gui.ElMensajeroGUI;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
            }, "Searching for all contacts").start();
        }
        @Override
        public void connectionError() {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initStyle(StageStyle.UTILITY);
                alert.setTitle("Erro de conexão");
                alert.setHeaderText("Erro ao tentar conexão com o servidor");
                alert.setContentText("Deseja tentar conectar novamente?");
                alert.setResult(ButtonType.OK);
                alert.getButtonTypes().setAll( 
                    new ButtonType( "Cancelar" ),
                    new ButtonType( "Tentar novamente", ButtonData.OK_DONE )
                );
                if ( alert.showAndWait().get().getButtonData() == ButtonData.OK_DONE ){
                    socketClient.start( UserDataProperties.getUserData() );
                } else {
                    Platform.exit();
                    System.exit(0);
                }
            });
        }
        @Override
        public void contactStatusUpdated(Contact contact) {
            addContact(contact);
        }
        @Override
        public void messageReceived(Message m) {
            new Thread(() -> {
                gui.addMessage(m);
            }, "Adding received message").start();
        }
        
    }
    
    public final void addContact(Contact c){
        if ( c != null ){
            new Thread(() -> {
                for (int i = 0; i < contacts.size(); i++) {
                    if ( contacts.get(i).equals( c ) ){
                        contacts.remove(i);
                        break;
                    }
                }
                contacts.add(c);
                contacts.sort( Contact.ContactComparator.getInstance() );
            },"Adding contact to list").start();
        }
    }
    
    public final void setContacts(Contact cs[]){
        if ( cs != null ){
            new Thread(() -> {
                contacts.setAll(Arrays.asList(cs));
                contacts.sort( Contact.ContactComparator.getInstance() );
            }, "Setting all contact list").start();
        }
    }
    
    private void startGUI(Contact user){
        gui.setUserData( user );            
        gui.show();
        socketClient.start( user );
    }
    
    private class LoginGUIListener implements LoginGUI.LoginListener{

        @Override
        public void tryLogin(String email, String password) {
            if ( email.isEmpty() || password.isEmpty() ){
                login.showError("Preencha todos os campos");
                return;
            }
            try {
                password = UserDataProperties.encode(password);
                ContactDB user = socketClient.login(new ContactDB(null, email, password, null));
                if ( user == null ){
                    login.showError("Usuário e/ou senha incorreto(s)");
                    return;
                }
                UserDataProperties.setUserData( user );
                startGUI(user);

            } catch (Exception ex) {
                Logger.getLogger(ElMensajero.class.getName()).log(Level.SEVERE, null, ex);
                login.showError("Algo deu errado");
            }
        }

        @Override
        public void tryRegister(String name, String email, String password, File imageFile, ProgressIndicator progress) {
            if ( name.isEmpty() || email.isEmpty() || password.isEmpty() || imageFile == null ){
                login.showError("Preencha todos os campos");
                return;
            }
            if ( !imageFile.exists() ){
                login.showError("Algo ocorreu errado na imagem");
                return;
            }
            System.out.println("Enviando imagem");
            String image = socketClient.sendFile(imageFile, progress);
            if ( image == null ){
                login.showError("Erro ao enviar imagem");
                return;
            }
            try {
            System.out.println("Imagem enviada");
                password = UserDataProperties.encode(password);
                ContactDB user = new ContactDB(name, email, password, image);
                int res = socketClient.newUser(user);
                switch ( res ){
                    case 1:
                        UserDataProperties.setUserData(user);
                        startGUI(new Contact(
                            name, email, image,
                            Contact.Status.ONLINE
                        ));
                        break;
                    case -1:
                        login.showError("E-Mail já está em uso");
                        break;
                    default:
                        login.showError("Erro no cadastro");
                }
            } catch (Exception ex) {
                Logger.getLogger(ElMensajero.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    @Override
    public void start(Stage stage) throws FileNotFoundException, Exception {
        stage.getIcons().add(new Image(new FileInputStream("./logo.png")));
        
        gui = new ElMensajeroGUI( stage, contacts, socketClient );
        login = new LoginGUI(stage);
        login.setLoginListener(new LoginGUIListener());
        
        new Thread(() -> {
            ContactDB user = UserDataProperties.getUserData();
            if ( user != null ){
                user = socketClient.login(user);
            }
            if ( user == null ){
                login.show();
            } else {
                startGUI(user);
            }
            
        }, "Beginning Thread").start();
        
        stage.setOnCloseRequest((WindowEvent t) -> {
            Platform.exit();
            System.exit(0);
        });
    }
    
    public static void main(String[] args){
        launch(args);
        
    }
    
}
