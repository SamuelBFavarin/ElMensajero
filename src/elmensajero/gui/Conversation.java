
package elmensajero.gui;



import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Samuel
 * Let's go guys 
 */
class Conversation extends BorderPane {

    public Conversation() {
        VBox vbox = new VBox();
        
        
        Image imgAvatar1 = new Image("file://avatar1.jpg");
        ImageView avatar1 = new ImageView(imgAvatar1);
        avatar1.setFitWidth(100);
        avatar1.setPreserveRatio(true);
        avatar1.setSmooth(true);
        avatar1.setCache(true);
        
        Image imgAvatar2 = new Image("file://avatar2.jpg");
        ImageView avatar2 = new ImageView(imgAvatar2);
        avatar2.setFitWidth(100);
        avatar2.setPreserveRatio(true);
        avatar2.setSmooth(true);
        avatar2.setCache(true);
        
        
        vbox.setPrefWidth(200);
        vbox.setStyle("-fx-background-color: #336699;");
        vbox.getChildren().add(avatar1);
        vbox.getChildren().add(avatar2);
        this.setLeft(vbox);
        
        
        
        
    }
    
}
