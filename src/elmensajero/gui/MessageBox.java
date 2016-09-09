
package elmensajero.gui;

import elmensajero.Message;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 *
 * @author Samuel
 */
public class MessageBox extends AnchorPane{
    
    
    public MessageBox(Message message, boolean sender) {
        
        Node guiMessage = initMessage(message);
        if(sender){
            this.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            guiMessage.setEffect(new DropShadow(3, Color.BLACK));
            guiMessage.setStyle("-fx-background-color: #1a1a1a;" + "border-radius: 30px;");
            
        }else{
            guiMessage.setEffect(new DropShadow(3, Color.GAINSBORO));
            guiMessage.setStyle("-fx-background-color: #595959;" + "border-radius: 30px;");
        }
        
        this.getChildren().add( guiMessage );
    }


    private Node initMessage(Message message){
        Label txt = new Label(message.getMessage());
        txt.setTextFill(Color.web("#FFFFFF"));
        txt.setFont(new Font("Arial",20));
        txt.setWrapText(true);
        txt.setAlignment(Pos.CENTER_RIGHT);        
        return new StackPane(txt);
    }

    
    
}
