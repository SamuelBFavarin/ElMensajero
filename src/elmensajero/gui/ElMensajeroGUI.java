
package elmensajero.gui;

import elmensajero.Contact;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author Vinicius
 */
public class ElMensajeroGUI extends BorderPane {

    public ObservableList<Contact> contacts;
    
    /**
     *
     * @param stage
     * @param contacts
     */
    public ElMensajeroGUI(Stage stage, List<Contact> contacts) {
        this.contacts = FXCollections.observableList(contacts);
        init(stage);
    }
    
    private void init(Stage stage){
        
        this.setLeft(new Contacts( contacts ));
        this.setCenter(new Conversation());
        
        Scene scene = new Scene( this, 800, 500 );
        stage.setTitle("El Mensagero");
        stage.setMinWidth(700);
        stage.setScene(scene);
        stage.show();
        
    }
    
}
