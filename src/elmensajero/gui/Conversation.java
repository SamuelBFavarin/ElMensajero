
package elmensajero.gui;



import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToolBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 *
 * @author Samuel
 * This class make 
 */
class Conversation extends BorderPane {

    public Conversation() {
 
        this.setLeft(initLeftBox());  
        this.setCenter(initMainBox());
    }
    
    
    private Node initMainBox(){
        BorderPane mainbox = new BorderPane();
        mainbox.setStyle("-fx-background-color: #20B2AA;");
        
        HBox hbox = new HBox();
        TextArea txtArea = initTextArea();
        Button sendButton = initSendButton();
        hbox.setMargin(txtArea,new Insets(50,20,15,20));
        hbox.setMargin(sendButton,new Insets(50,10,0,0));
        hbox.getChildren().add(txtArea);
        hbox.getChildren().add(sendButton);
        
        mainbox.setBottom(hbox);
        return mainbox;
    }
    
    private TextArea initTextArea(){
        TextArea txtArea = new TextArea();
        txtArea.setMaxHeight(125);
        txtArea.setWrapText(true);
        txtArea.setStyle("-fx-font-size: 20pt;");
        return txtArea; 
    }
    
    private Button initSendButton(){
        Button send = new Button("ENVIAR");
        send.setMinSize(125, 125);
        send.setTextFill(Color.web("#FFFFFF"));
        send.setStyle(
                "-fx-font-size: 20pt;"
              + "-fx-background-color:#008B8B;");
        return send;
    }
    private Node initLeftBox(){
        VBox vbox = new VBox();
        Label friendName = initFriendName();
        ImageView friendImage = initFriendImage();
        vbox.setPrefWidth(200);
        vbox.setStyle("-fx-background-color: #008B8B;");
        vbox.setSpacing(2);
        vbox.getChildren().add(friendName);
        vbox.getChildren().add(friendImage);
               
        vbox.setMargin(friendName,new Insets(30,1,2,25));
        vbox.setMargin(friendImage,new Insets(0,1,30,20));
        
        return vbox;
    }
    
    private Label initFriendName(){
        Label txtName = new Label("Megan Fox");
        txtName.setTextFill(Color.web("#FFFFFF"));
        txtName.setFont(new Font("Arial",20));
        txtName.setEffect(new DropShadow(5, Color.WHITE));  
        return txtName;
    }
    
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
