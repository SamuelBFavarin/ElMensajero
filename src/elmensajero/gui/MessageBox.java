
package elmensajero.gui;

import elmensajero.Message;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Region;
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
public class MessageBox extends StackPane{
   
    /**
    * Construtor da MessageBox
    * Cria variáveis para cores e define o alinhamento do texto na label
    * Testa se a mensagem está sendo recebida ou enviada e cria a label
    * 
    */
    
    public MessageBox(Message message, boolean sender) {
        String black = "#FFFFFF";
        String white = "#000000";
        this.setAlignment(Pos.BOTTOM_CENTER);
        if(sender){
            this.setEffect(new DropShadow(3, Color.BLACK));
            this.setStyle("-fx-background-color: #616161;" + "border-radius: 30px;"+ "-fx-padding: 10;");
            this.getChildren().add( initMessage(message,black));
            
        }else{
            this.setEffect(new DropShadow(3, Color.GREY));
            this.setStyle("-fx-background-color: #E0E0E0;" + "border-radius: 30px;" + "-fx-padding: 10;");
            this.getChildren().add( initMessage(message,white));
        }
        
    }
    
    /**
    * Função initMessage
    * Recebe por parametro uma menssagem do tipo Message e uma cor do tipo String
    * Cria a Label e define suas propriedades
    */

    private Label initMessage(Message message,String txtColor){
        Label txt = new Label(message.getMessage());
        txt.setMinSize(100, 30);
        txt.setTextFill(Color.web(txtColor));
        txt.setFont(new Font("Arial",20));
        txt.setWrapText(true);
        return txt;
    }

    
    
}
