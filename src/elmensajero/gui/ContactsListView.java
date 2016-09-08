
package elmensajero.gui;

import elmensajero.Contact;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Classe estendendo ListView para uma modularizar o codigo.
 * Configura a listview para ficar com as definicoes desejadas
 * 
 * @see javafx.scene.control.ListView;
 *
 * @author Vinicius Santos
 */
class ContactsListView extends ListView<Contact> implements EventHandler<MouseEvent> {

    private ContactClicked contactClickedListener;
    
    /**
     * Inicializa os atributos da classe e chama funcao de
     * inicializacao da interface grafica.
     * 
     * @param contacts
     */
    public ContactsListView(ObservableList<Contact> contacts) {
        super();
        contactClickedListener = null;
        init(contacts);
    }
    
    /**
     * Configura as informacoes do ListView.
     * define a lista observavel e o comportamento
     * das celulas
     * 
     * @param contacts 
     */
    private void init(ObservableList<Contact> contacts){
        this.setItems(contacts);
        this.setPadding(new Insets(2,0,0,2));
        this.setCellFactory((ListView<Contact> lv) -> new ContactCell());
        this.setOnMouseClicked( this );
    }
    
    /**
     * Classe para comportamento das celulas.
     * Define o comportamento para que na celula tenha 
     * um quadrado mostrando o status do usuario
     * 
     * @see javafx.scene.control.ListCell
     */
    private class ContactCell extends ListCell<Contact> {
        private final Rectangle rect;
        private final Color onlineColor;
        private final Color offlineColor;
        /**
         * Instancia os atributos.
         */
        private ContactCell() {
            this.rect = new Rectangle(10,10);
            this.onlineColor = new Color( 0.2, 1, 0.2, 1 );
            this.offlineColor = new Color( 0.8, 0.8, 0.8, 1 );
        }
        /**
         * Coloca o texto na celula e o quadrado 
         * indicando o status do usuario
         */
        @Override
        protected void updateItem(Contact contact, boolean empty) {
            super.updateItem(contact, empty);
            if ( empty ){ 
                setText(null);
                return;
            };
            setText(contact.getName());
            rect.setFill( (contact.getStatus()==Contact.Status.ONLINE) ? onlineColor : offlineColor );
            setGraphic(rect);
        } 
    }
    
    /**
     * Define o listener de contato clicado.
     * 
     * @param listener
     */
    public void setContactClicked(ContactClicked listener){
        contactClickedListener = listener;
    }
    
    /**
     * Interface que e chamada ao ter algum contato clicado.
     */
    public interface ContactClicked {
        public void contactClicked(Contact contact);
    }
    
    /**
     * Listener chamado pela interface grafica.
     * Quando o ListView e clicado, e buscado o item
     * selecionado do ListView e se e realmente um contato,
     * ira chamar o metodo do listener de contato clicado se 
     * esse estiver definido
     * 
     * @param event
     */
    @Override
    public void handle(MouseEvent event) {
        Contact c = ContactsListView.this.getSelectionModel().getSelectedItem();
        if ( c == null || contactClickedListener == null )
            return;
        contactClickedListener.contactClicked(c);
    }
    
}
