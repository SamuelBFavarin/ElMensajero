
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
     * Define o layout da tela, com cor e espaçamento dos elementos
     * Chama os metodos necessarios da inteface grafica da classe
     * 
     */
    public ConversationTopPane() {
        friendName = initFriendName();
        friendImage = initFriendImage();
        
        StackPane logoImage = new StackPane(initLogoImage());
        StackPane friendNamePane = new StackPane(friendName);
        StackPane friendImagePane = new StackPane(friendImage);
        
        this.maxHeight(100);
        this.setStyle("-fx-background-color: #212121");
        
        friendImagePane.setPadding(new Insets(5,5,5,5));
        logoImage.setPadding(new Insets(0,5,0,0));
        
        friendNamePane.setAlignment(Pos.CENTER_LEFT);
        friendImagePane.setAlignment(Pos.BASELINE_CENTER);
        logoImage.setAlignment(Pos.CENTER_RIGHT);
        
        this.getChildren().add(friendImagePane);
        this.getChildren().add(friendNamePane);
        this.getChildren().add(logoImage);
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
        txtName.setFont(Font.font("Trebuchet MS", 18));
        txtName.setEffect(new DropShadow(1, Color.WHITE));
        txtName.setPadding(new Insets(0,0,0,10));
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
        avatar.setFitHeight(70);
//        avatar.setPreserveRatio(true);
//        avatar.setSmooth(true);
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
            Image logoImage = new Image(
                new FileInputStream("./logo.png"),
                70,70, // width X height
                true, true // preserveRatio // smooth
            );
            logo = new ImageView(logoImage);
            logo.setFitHeight(70);
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
