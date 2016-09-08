
package elmensajero.gui;

import elmensajero.Contact;
import javafx.application.Platform;

import javafx.collections.ObservableList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

/**
 * Classe da interface grafica da barra da esquerda.
 * BorderPane foi a melhor opcao de layout para que
 * houvesse o campo de texto acima, lista no centro
 * e informacoes do usuario abaixo
 * 
 * @see javafx.scene.layout.BorderPane
 * 
 * @author Vinicius Santos
 */
class Contacts extends BorderPane{
    
    private final ContactsListView contactsListView;
    private final FilterField filterField;
    
    /**
     * Metodo contrutor que inicializa interface grafica.
     * Inicializa o campo de filtro de texto para busca 
     * dos contatos com os contatos e envia a lista de 
     * contatos filtrados para o construtor da lista
     * grafica de contatos
     * 
     * @see javafx.collections.ObservableList
     * 
     * @see elmensajero.gui.FilterField
     * @see elmensajero.gui.ContactsListView
     * 
     * @author Vinicius
     * @param contacts
     */
    public Contacts(ObservableList<Contact> contacts){
        super();
        
        filterField = new FilterField(contacts);
        
        contactsListView = new ContactsListView( filterField.getFilteredList() );
        
        this.setTop( filterField );
        this.setCenter( contactsListView );
        this.setPadding(new Insets(2,2,2,2));
        
        this.setPrefWidth(200);
        this.setMaxWidth(300);
    }
    
    /**
     * Metodo para definir informacoes do usuario.
     * Para uso posterior ao construct, pois pode
     * haver demora na obtencao dos dados do usuario,
     * utilizado Platform.runLater() para que nao tenha
     * demora ao inicializar a interface. Adiciona um novo
     * componente ao BorderPane da classe
     * 
     * @see javafx.application.Platform
     * 
     * @param userData
     */
    public void setUserData(Contact userData){
        Platform.runLater(() -> {
            this.setBottom( initContactData(userData) );
        });
    }
    
    /**
     * Metodo para instanciar interface grafica de
     * informacoes do usuario.
     * Instancia o componente de acordo com os dados
     * recebidos por parametro
     * 
     * @return componente da interface grafica
     * 
     * @param userData
     */
    private Node initContactData(Contact userData){
        ImageView imageView = new ImageView(userData.getImage());
        imageView.setFitWidth(60);
        imageView.setFitHeight(60);
        
        Label nameLabel = new Label(userData.getName());
        Label emailLabel = new Label(userData.getEmail());
        emailLabel.setTextFill(Paint.valueOf("#1976D2"));
        
        VBox contact = new VBox( nameLabel, emailLabel );
        contact.setAlignment(Pos.CENTER_LEFT);
        HBox contactBox = new HBox( 10, imageView, contact );
        contactBox.setStyle(
            "-fx-background-color:#FFFFFF;"
           +"-fx-border-color: #BBDEFB;"
           +"-fx-border-width: 1px;"
        );
        contactBox.setPadding(new Insets(5,5,5,5));
        contactBox.setAlignment(Pos.CENTER);
        
        return contactBox;
    }
    
    /**
     * Metodo para definir um listener de quando o 
     * contato for clicado.
     * Lancado pela classe inferior ContactsListView
     * 
     * @param listener
     */
    public void setContactClicked(ContactsListView.ContactClicked listener){
        contactsListView.setContactClicked(listener);
    }
    
}
