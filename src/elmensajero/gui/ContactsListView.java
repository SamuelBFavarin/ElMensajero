
package elmensajero.gui;

import elmensajero.Contact;
import elmensajero.Message;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Classe estendendo ListView para uma modularizar o codigo.
 * Configura a listview para ficar com as definicoes desejadas
 * 
 * @see javafx.scene.control.ListView;
 *
 * @author Vinicius Santos
 */
class ContactsListView extends ListView<Contact> implements EventHandler<MouseEvent> {
    
    private final Map<String,Integer> unreadMessages;

    private ContactClicked contactClickedListener;
    
    /**
     * Inicializa os atributos da classe e chama funcao de
     * inicializacao da interface grafica.
     * 
     * @param contacts
     */
    public ContactsListView(ObservableList<Contact> contacts) {
        super();
        unreadMessages = new HashMap<>();
        contactClickedListener = null;
        init(contacts);
    }
    
    /**
     * Adiciona mensagem não lida a lista.
     * Incrementa a contagem de mensagens nao lidas no 
     * Map de mensagens nao lidas e forca a atualizacao
     * das celulas da lista
     * 
     * @param message 
     */
    public void addUnreadMessage(Message message){
        Integer countMessages = unreadMessages.get(message.getSender().getEmail());
        if ( countMessages == null ){
            unreadMessages.put(message.getSender().getEmail(), 1);
        } else {
            unreadMessages.put(message.getSender().getEmail(), 1 + countMessages);
        }
        Platform.runLater(() -> {
            this.refresh();
        });
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
        private final Rectangle left;
        private final StackPane right;
        private final Label rightLabel;
        private final Color onlineColor;
        private final Color offlineColor;
        private final Label nameLabel;
        private final Node node;
        
        /**
         * Instancia os atributos.
         */
        private ContactCell() {
            this.onlineColor = new Color( 0.2, 1, 0.2, 1 );
            this.offlineColor = new Color( 0.8, 0.8, 0.8, 1 );
            
            this.left = new Rectangle( 10,10 );
            HBox.setMargin(left, new Insets(3, 2, 0, 0));
            
            this.nameLabel = new Label("");
            this.nameLabel.setPadding(new Insets(0,5,0,5));
            this.nameLabel.setAlignment(Pos.CENTER_LEFT);
            
            StackPane center = new StackPane(nameLabel);
            center.setAlignment(Pos.CENTER_LEFT);
            HBox.setHgrow(center, Priority.ALWAYS);
            
            this.right = new StackPane();
            this.rightLabel = new Label("");
            this.rightLabel.setFont(Font.font("Tahoma", FontWeight.BOLD, 8));
            Circle circle = new Circle( 6,5, 6, Color.AQUA );
            this.rightLabel.setAlignment(Pos.CENTER);
            this.right.getChildren().addAll( circle, this.rightLabel );
            this.right.setVisible(false);
            
            this.node = new HBox( left, center, right );
            
        }
        /**
         * Coloca o texto na celula e o quadrado indicando o status do usuario.
         * Desenha tambem o numero de mensagens nao lidas caso tenha alguma
         */
        @Override
        protected void updateItem(Contact contact, boolean empty) {
            super.updateItem(contact, empty);
            setText(null);
            if ( empty ){
                setGraphic(null);
                return;
            }
            
            Integer unreadCount = unreadMessages.get(contact.getEmail());
            if ( unreadCount == null ){
                right.setVisible(false);
            } else {
                right.setVisible(true);
                rightLabel.setText(unreadCount.toString());
            }
            
            nameLabel.setText(contact.getName().split(" ")[0]);
            left.setFill( (contact.getStatus()==Contact.Status.ONLINE) ? onlineColor : offlineColor );
            
            setGraphic(this.node);
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
        Contact contact = ContactsListView.this.getSelectionModel().getSelectedItem();
        if ( contact == null || contactClickedListener == null )
            return;
        unreadMessages.remove(contact.getEmail());
        this.refresh();
        contactClickedListener.contactClicked(contact);
    }
    
}
