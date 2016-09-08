
package elmensajero.gui;

import elmensajero.Message;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 *
 * @author Samuel
 */
public class MessageBox extends StackPane{
    
    
    public MessageBox(Message message, boolean sender) {
        this.setAlignment(Pos.BOTTOM_LEFT);
        if(sender){
            this.setEffect(new DropShadow(3, Color.BLACK));
            this.setStyle("-fx-background-color: #008B8B;" + "border-radius: 30px;");
            
        }else{
            this.setEffect(new DropShadow(3, Color.GAINSBORO));
            this.setStyle("-fx-background-color: #05AAAA;" + "border-radius: 30px;");
        }
        this.getChildren().add( initMessage(message));
    }


    private Label initMessage(Message message){
        Label txt = new Label(message.getMessage());
        txt.setTextFill(Color.web("#FFFFFF"));
        txt.setFont(new Font("Arial",20));
        txt.setWrapText(true);
        return txt;
    }

    
    
}
