
package elmensajero.gui;

import elmensajero.Contact;
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
 * @see javafx.scene.layout.BorderPane
 * 
 * @author Vinicius, Samuel, Lucas
 */
public class ElMensajeroGUI extends BorderPane {
    
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
        contactsBox.setContactClicked((Contact contact) -> {
            System.out.println(contact.getName()+" clicado!");
        });
        
        conversation = new Conversation();
        
        this.setLeft(contactsBox);
        this.setCenter(conversation);
        this.setStyle("-fx-focus-color: transparent");
        
        Scene scene = new Scene( this, 800, 500 );
        stage.setTitle("El Mensajero");
        stage.setMinHeight(500);
        stage.setMinWidth(700);
        stage.setScene(scene);
        stage.show();
        
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
