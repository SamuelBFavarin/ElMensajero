
package elmensajero.gui;

import elmensajero.Contact;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 *
 * @author Samuel
 * Classe que gera a parte esquerda da tela de conversa
 * Possui o nome e a imagem do parcero de conversa
 */
public class ConversationTopPane extends StackPane {
 
    private final ImageView friendImage;
    private final Label friendName;

    /**
     * Construtor da classe ConversationTopPane
     * Define o designer da tela, com cor e espaçamento dos elementos
     * Chama os metodos necessarios da inteface grafica da classe
     * 
     */
    public ConversationTopPane() {
        friendName = initFriendName();
        friendImage = initFriendImage();
        ImageView logoImage = initLogoImage();
        this.maxHeight(100);
        this.setStyle("-fx-background-color: #212121");
        this.getChildren().add(friendImage);  
        this.getChildren().add(friendName);    
        this.getChildren().add(logoImage);
        this.setMargin(friendName,new Insets(10,1,2,100));
        this.setMargin(friendImage,new Insets(10,1,5,10));
        this.setMargin(logoImage,new Insets(10,20,5,10));
        this.setAlignment(Pos.CENTER_LEFT);
        this.setAlignment(logoImage, Pos.CENTER_RIGHT);
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
        txtName.setFont(new Font("Arial",17));
        txtName.setEffect(new DropShadow(3, Color.WHITE));  
        return txtName;
    }
 
    /**
     * Método da classe ConversationTopPane
     * Gera uma ImageView (Imagem) do usuário
     * Define as propriedade da Imagem
     * Define sombra
     */
    private ImageView initFriendImage(){
        ImageView avatar = new ImageView();
        avatar.setFitWidth(70);
        avatar.setPreserveRatio(true);
        avatar.setSmooth(true);
        avatar.setCache(true);
        avatar.setEffect(new DropShadow(15, Color.BLACK));
        return avatar;
    }
   
    /**
     * Método da classe ConversationTopPane
     * Gera uma ImageView (Imagem) da logo
     * Define as propriedade da Imagem
     * Define sombra
     */
    private ImageView initLogoImage() {
        ImageView logo = null;
        try {
            logo = new ImageView(new Image(new FileInputStream("./logo.png")));
            logo.setFitWidth(70);
            logo.setPreserveRatio(true);
            logo.setSmooth(true);
            logo.setCache(true);
            logo.setEffect(new DropShadow(15, Color.BLACK));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        return logo;
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
