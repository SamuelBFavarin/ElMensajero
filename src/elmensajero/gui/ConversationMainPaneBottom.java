
package elmensajero.gui;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
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
    * Instancia dois Botões com a funçao initButton
    * Define as margens do botao e do TextArea
    * Adiciona ambos no HBox
    * 
    * @see javafx.beans.value.ChangeListener
    */
    public ConversationMainPaneBottom() {
        TextArea txtArea = initTextArea();
        Button sendButton = initButton("ENVIAR");
        Button arribaButton = initButton("ARRIBA");

        this.setMargin(txtArea,new Insets(50,20,15,15));
        this.setMargin(sendButton,new Insets(50,15,0,15));
        this.setMargin(arribaButton,new Insets(50,15,0,0));
        this.getChildren().add(txtArea);
        this.getChildren().add(sendButton);
        this.getChildren().add(arribaButton);
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
    private Button initButton(String name){
        Button btn = new Button(name);
        btn.setCursor(Cursor.HAND);
        btn.setMinSize(125, 125);
        btn.setTextFill(Color.web("#FFFFFF"));
        btn.setStyle(
            "-fx-font-size: 15pt;"
          + "-fx-background-color: #1a1a1a"
        );
        return btn;
    }
    
    
}
