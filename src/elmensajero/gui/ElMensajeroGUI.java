
package elmensajero.gui;

import elmensajero.Contact;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Interface Grafica Principal.
 * Classe principal do programa para interface grafica que
 * extende BorderPane pois a diposicao correspondente foi
 * a desejada no desenvolvimento do layout
 * 
 * @see javafx.scene.Scene
 * 
 * @author Vinicius, Samuel, Lucas
 */
public class ElMensajeroGUI extends BorderPane {
    
    private static final double WIDTH = 700, HEIGHT = 500;
    private final Scene scene;
    private final Stage stage;
    private final Contacts contactsBox;
    private final Conversation conversation;
    
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
     * @param contacts
     */
    public ElMensajeroGUI(Stage stage, ObservableList<Contact> contacts) {
        super();
        
        contactsBox = new Contacts(contacts);
        conversation = new Conversation();
        
        contactsBox.setContactClicked((Contact contact) -> {
            System.out.println(contact.getName()+" clicado!");
        });
        
        this.setLeft(contactsBox);
        this.setCenter(conversation);
        this.setStyle("-fx-focus-color: transparent");
        
        this.scene = new Scene(this, WIDTH, HEIGHT);
        this.stage = stage;
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
            stage.setX(stage.getX() + stage.getWidth() / 2 - childStage.getWidth() / 2);
            stage.setY(stage.getY() + stage.getHeight() / 2 - childStage.getHeight() / 2);
            stage.setScene(scene);
            stage.show();
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
    public void setUserData(Contact userData){
        contactsBox.setUserData(userData);
    }
    
}
