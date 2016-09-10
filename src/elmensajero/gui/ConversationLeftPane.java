
package elmensajero.gui;

import elmensajero.Contact;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 *
 * @author Samuel
 * Classe que gera a parte esquerda da tela de conversa
 * Possui o nome e a imagem do parcero de conversa
 */
public class ConversationLeftPane extends VBox {
    
    private final ImageView friendImage;
    private final Label friendName;
    
    /**
     * Construtor da classe ConversationLeftPane
     * Define o designer da tela, com cor e espaçamento dos elementos
     * Chama as funções da classe
     * 
     */
    public ConversationLeftPane() {
        friendName = initFriendName();
        friendImage = initFriendImage();
        this.setPrefWidth(200);
        this.setStyle("-fx-background-color: #1a1a1a");
        this.setSpacing(2);
        this.getChildren().add(friendName);
        this.getChildren().add(friendImage);      
        this.setMargin(friendName,new Insets(30,1,2,25));
        this.setMargin(friendImage,new Insets(0,1,30,20));
    }
    
    /**
     * Método da classe ConversationLeftPane
     * Gera uma Label para mostrar o nome na tela
     * Define o designer da Label, alterando 
     * cor, sombra, tamanho e fonte
     */
    private Label initFriendName(){
        Label txtName = new Label();
        txtName.setTextFill(Color.web("#FFFFFF"));
        txtName.setFont(new Font("Arial",20));
        txtName.setEffect(new DropShadow(5, Color.WHITE));  
        return txtName;
    }
 
    /**
     * Método da classe ConversationLeftPane
     * Gera uma ImageView (Imagem) do usuário
     * Define as propriedade da Imagem
     * Define sombra
     */
    private ImageView initFriendImage(){
        ImageView avatar = new ImageView();
        avatar = new ImageView();
        avatar.setFitWidth(150);
        avatar.setPreserveRatio(true);
        avatar.setSmooth(true);
        avatar.setCache(true);
        avatar.setEffect(new DropShadow(15, Color.BLACK));
        return avatar;
    }
    
    
    /**
     * Define os componentes da interface grafica para o contato
     * recebido por parametro.
     * 
     * @param contact
     */
    public void setContact(Contact contact){
        Platform.runLater(() -> {
            friendName.setText( contact.getName() );
            try {
                friendImage.setImage( new Image(contact.getImage(), true) );
            } catch ( Exception e) {
                e.printStackTrace();
                friendImage.setImage(null);
            }
        });
    }
}
