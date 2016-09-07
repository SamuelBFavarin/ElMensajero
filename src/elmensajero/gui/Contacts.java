
package elmensajero.gui;

import elmensajero.Contact;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Vinicius
 */
public class Contacts extends VBox{
    
    public ObservableList<Contact> contacts;
    public ListView<Contact> list;
    
    /**
     * 
     * @author Vinicius
     * @param contacts
     */
    public Contacts(ObservableList<Contact> contacts){
        this.contacts = contacts;
        
        TextField filterField = new TextField();
        filterField.setPromptText("Buscar Contatos");
        filterField.setFocusTraversable(false);
        
        list = new ListView<>();
        
        list.setItems(contacts);
        
        /*list.setCellFactory(lv -> {
            return new ListCell<Contact>(){
                Rectangle rect = new Rectangle(10,10);
                @Override
                protected void updateItem(Contact contact, boolean bln) {
                    super.updateItem(contact, bln);
                    if (contact != null) {
                        setText(contact.getName());
                        rect.setFill(new Color( 0.2, 1, 0.2, 1 ));
                        setGraphic(rect);
                    }
                }
            };               
        });*/
        

        
        
        this.getChildren().add(filterField);
        this.getChildren().add(list);
        this.setPrefWidth(200);
        this.setMaxWidth(300);
    }
   
}
