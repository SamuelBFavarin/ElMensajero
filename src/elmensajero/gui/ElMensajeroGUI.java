package elmensajero.gui;

import elmensajero.Contact;
import elmensajero.Message;
import elmensajero.data.RetrieveDataListener;
import elmensajero.data.base.ContactDB;
import java.util.Calendar;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Interface Grafica Principal.
 * Classe principal do programa para interface grafica que
 * extende BorderPane pois a diposicao correspondente foi
 * a desejada no desenvolvimento do layout
 * Gerencia atualizacoes da interface grafica lancadas pela
 * classe principal
 * 
 * @see javafx.scene.Scene
 * 
 * @author Vinicius
 */
public class ElMensajeroGUI extends BorderPane {
    
    private static final double WIDTH = 700, HEIGHT = 500;
    
    private final Scene scene;
    private final Stage stage;
    private final AppMenuBar menuBar;
    private final Contacts contactsBox;
    private final Conversation conversation;
    
    private Contact userData, contact;
    private final RetrieveDataListener retrieveDataListener;
    
    /**
     * Recebe a lista inicial de contatos para ser enviada
     * para classes do escopo
     * 
     * Instancia os componentes de interface grafica necessarios e modifica
     * as propriedades da janela para configuracoes pre definidas
     * 
     * @see elmensajero.gui.Contacts
     * @see elmensajero.gui.Conversation
     * 
     * @param stage
     * @param loginGUI
     * @param contacts
     * @param retrieveDataListener
     */
    public ElMensajeroGUI(Stage stage, LoginGUI loginGUI, ObservableList<Contact> contacts, RetrieveDataListener retrieveDataListener) {
        super();
        this.contact = null;
        this.retrieveDataListener = retrieveDataListener;
        
        this.stage = stage;
        
        menuBar = new AppMenuBar(stage, retrieveDataListener, loginGUI, this);
        contactsBox = new Contacts(contacts);
        conversation = new Conversation();
        
        scene = initScene();
        initListeners();
    }
    
    /**
     * Inicializa as propriedades do componente de interface grafica e 
     * a scene principal
     */
    private Scene initScene()
    {
        this.setLeft(contactsBox);
        this.setCenter(conversation);
        this.setTop(menuBar);
        this.setStyle("-fx-focus-color: transparent");    
        return new Scene(this, WIDTH, HEIGHT);
    }
    
    /**
     * Define os listeners da interface grafica.
     * Define os listeners de acordo com a acao necessaria
     * para execucao
     */
    private void initListeners(){
        contactsBox.setContactClicked((Contact c) -> {
            loadConversation(c);
        });
        conversation.setSendButtonClickedListener((String message) -> {
            Message m = new Message(
                userData,
                contact,
                message,
                Calendar.getInstance().getTime()
            );
            new Thread(() -> {
                if ( retrieveDataListener.sendMessage(m) ){
                    addMessage(m);
                } else {
                    showError("Falha ao enviar mensagem: "+m.getMessage());
                }
            }, "Send Message").start();
        });
        
    }
    
    /**
     * Carrega a conversa com o usuario enviado por parametro.
     * 
     * @param contact
     */
    private void loadConversation(Contact contact){
        this.contact = contact;
        new Thread(() -> {
            conversation.setContact(contact);
            Message[] messages = retrieveDataListener.getAllMessages(userData, contact);
            setMessages(messages);
        }, "Load conversation").start();
    }
    
    /**
     * Define a classe como o scene da stage e mostra o stage.
     * 
     * @see javafx.stage.Stage
     * @see javafx.scene.Scene
     */
    public void show(){
        Platform.runLater(() -> {
            stage.setTitle("El Mensajero");
            stage.setMinHeight(HEIGHT);
            stage.setMinWidth(WIDTH);
            stage.setScene(scene);
            stage.show();
        });
    }
    
    /**
     * Mostra uma mensagem de erro na interface grafica.
     * @param message
     */
    public void showError(String message){
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Atenção");
            alert.setHeaderText("Algo ocorreu errado");
            alert.setContentText(message);
            alert.showAndWait();
        });
    }
    
    /**
     * Metodo para uso posterior, somente chamado ao
     * carregar as informacoes de usuario
     * 
     * @see elmensajero.gui.Contacts
     * 
     * @param userData
     */
    public void setUserData(ContactDB userData){
        this.userData = userData;
        menuBar.setUserData(userData);
        contactsBox.setUserData(userData);
    }
    
    /**
     * Adiciona uma mensagem na interface grafica.
     * Chama a classe inferior "dona" dessa secao da
     * interface grafica.
     * Trata para onde enviar, se para a contacts ou
     * para conversation
     * 
     * @param message
     */
    public void addMessage(Message message){
        if ( message.getSender().equals(userData) || message.getSender().equals(contact) ){
            conversation.addMessage( message, this.userData );
        } else {
            contactsBox.addUnreadMessage(message);
        }
    }
    
    /**
     * Limpa as mensagens e adiciona as mensagens na interface grafica.
     * Chama a classe inferior "dona" dessa parte da interface grafica
     * 
     * @param messages
     */
    public void setMessages(Message[] messages){
        conversation.setMessages( messages, this.userData );
    }
    
    /**
     * Define o atributo atual usuario aberto na interface grafica.
     * 
     * @param contact
     */
    public void setContact(Contact contact){
        loadConversation(contact);
    }
    
}
