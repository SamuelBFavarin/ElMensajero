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
 * classe MessageBox extendida de StackPane
 * Define o layout de uma mensagem
 * Cria uma label para a mensagem
 * Define se a mensagem está sendo enviada ou recebida
 * 
 * @author Samuel
 * @see javafx.beans.value.ChangeListener
 */
   
    
public class MessageBox extends AnchorPane{
    
    /**
    * Construtor da MessageBox
    * Cria variáveis para cores e define o alinhamento do texto na label
    * Testa se a mensagem está sendo recebida ou enviada e cria a label
    * 
    */
    
    public MessageBox(Message message, boolean sender) {
        String white  = "#FFFFFF";
        String black  = "#000000";
        Node guiMessage;
        if(sender){
            this.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            guiMessage = initMessage(message,white);
            guiMessage.setEffect(new DropShadow(3, Color.BLACK));
            guiMessage.setStyle("-fx-background-color: #616161;" );
           
            
        }else{
            guiMessage = initMessage(message,black);
            guiMessage.setEffect(new DropShadow(3, Color.GAINSBORO));
            guiMessage.setStyle("-fx-background-color: #E0E0E0;");
        }
        this.getChildren().add(guiMessage);
    }
    
    /**
    * Função initMessage
    * Recebe por parametro uma menssagem do tipo Message e uma cor do tipo String
    * Cria a Label e define suas propriedades
    */

    private Node initMessage(Message message,String txtColor){
        Label txt = new Label(message.getMessage());
        txt.setMinSize(100, 30);
        txt.setTextFill(Color.web(txtColor));
        txt.setFont(new Font("Arial",20));
        txt.setWrapText(true);
        txt.setAlignment(Pos.CENTER);        
        return new StackPane(txt);
    }

    
    
}
