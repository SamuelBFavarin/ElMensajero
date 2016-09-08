
package elmensajero.gui;

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
    
 /**
 * Construtor da classe ConversationLeftPane
 * Define o designer da tela, com cor e espaçamento dos elementos
 * Chama as funções da classe
 * 
 */
    public ConversationLeftPane() {
        Label friendName = initFriendName();
        ImageView friendImage = initFriendImage();
        this.setPrefWidth(200);
        this.setStyle("-fx-background-color: #008B8B;");
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
        Label txtName = new Label("Megan Fox");
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
        Image imgAvatar = new Image("http://sizlingpeople.com/wp-content/uploads/2016/05/Megan-Fox-Instagram.jpg");
        ImageView avatar = new ImageView(imgAvatar);
        avatar.setFitWidth(150);
        avatar.setPreserveRatio(true);
        avatar.setSmooth(true);
        avatar.setCache(true);
        avatar.setEffect(new DropShadow(15, Color.BLACK));
        return avatar;
    }
    
   
}
