
package elmensajero.gui;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

/**
 * classe ConversationMainPaneBottom.
 * Define a parte inferior da classe ConversationMainPane
 * Cria o TextArea para escrever as mensagens 
 * Cria o Button para enviar as mensagens
 * 
 * @author Samuel
 * @see javafx.beans.value.ChangeListener
 */
public class ConversationMainPaneBottom extends HBox {
    
    /**
    * classe ConversationMainPaneBottom.
    * Instacina o TextArea com a função initTextArea
    * Instancia o Botao com a funçao initSendButton
    * Define as margens do botao e do TextArea
    * Adiciona ambos no HBox
    * 
    * @see javafx.beans.value.ChangeListener
    */
    public ConversationMainPaneBottom() {
        TextArea txtArea = initTextArea();
        Button sendButton = initSendButton();

        this.setMargin(txtArea,new Insets(50,20,15,20));
        this.setMargin(sendButton,new Insets(50,10,0,0));
        this.getChildren().add(txtArea);
        this.getChildren().add(sendButton);
    }
    
    /**
    * Método chamado pelo Construtor
    * Cria o TextArea e define suas propriedades
    * 
    * @see javafx.beans.value.ChangeListener
    */
    private TextArea initTextArea(){
        TextArea txtArea = new TextArea();
        txtArea.setMaxHeight(125);
        txtArea.setWrapText(true);
        txtArea.setStyle("-fx-font-size: 15pt;");
        txtArea.setEffect(new DropShadow(6, Color.BLACK));  
        return txtArea; 
    }
    
    /**
    * Método chamado pelo Construtor
    * Cria o Botão e define suas propriedades
    * 
    * @see javafx.beans.value.ChangeListener
    */
    private Button initSendButton(){
        Button send = new Button("ENVIAR");
        send.setMinSize(125, 125);
        send.setTextFill(Color.web("#FFFFFF"));
        send.setStyle(
            "-fx-font-size: 15pt;"
          + "-fx-background-color: #1a1a1a"
        );
        return send;
    }
    
    
}
