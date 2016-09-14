package elmensajero.gui;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
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
public class ConversationMainPaneBottom extends HBox implements EventHandler<MouseEvent> {
    
    private SendButtonClickedListener sendButtonClickedListener;
    
    private TextArea txtArea;
    
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
        sendButtonClickedListener = null;

        
        initTextArea();
        Button sendButton = initButton("ENVIAR");
        Button arribaButton = initButton("ARRIBA");

        
        sendButton.setOnMouseClicked(this);
        
        arribaButton.setOnMouseClicked((MouseEvent event) -> {
            if ( this.sendButtonClickedListener == null ) return;
            sendButtonClickedListener.sendMessage("ARRIBA");
            System.out.println("ARRIBA");
        });
        
        txtArea.addEventHandler(KeyEvent.KEY_TYPED, (KeyEvent event) -> {
            if ( event.getCode() == KeyCode.ENTER ){
                sendButton.fire();
            }
        });

        HBox.setHgrow(txtArea, Priority.ALWAYS);
        
        HBox.setMargin(txtArea,new Insets(50,20,15,15));
        HBox.setMargin(sendButton,new Insets(50,15,0,15));
        HBox.setMargin(arribaButton,new Insets(50,15,0,0));

        
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
    private void initTextArea(){
        txtArea = new TextArea();
        txtArea.setMaxHeight(125);
        txtArea.setWrapText(true);
        txtArea.setEffect(new DropShadow(6, Color.BLACK));
        
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
    
    /**
     * Define o listener de clique do botao de enviar mensagem.
     * 
     * @see elmensajero.gui.ConversationMainPaneBottom.SendButtonClickedListener
     * 
     * @param listener
     */
    public void setSendButtonClickedListener(SendButtonClickedListener listener){
        this.sendButtonClickedListener = listener;
    }
    

    /**
     * Metodo chamado pela intergace grafica de quando o botao de enviar
     * mensagem for clicado.
     * 
     * @see javafx.event.EventHandler
     * @see elmensajero.gui.ConversationMainPaneBottom.SendButtonClickedListener
     * 
     * @param event
     */
    @Override
    public void handle(MouseEvent event) {
        if ( this.sendButtonClickedListener == null )
            return;
        sendButtonClickedListener.sendMessage( txtArea.getText() );
        txtArea.setText("");
    }
    

    
    /**
     * Interface do listener do clique do botao de enviar mensagem da
     * interface grafica.
     * 
     * Deve ser setado pelo metodo setSendButtonClickedListener
     * 
     */
    public interface SendButtonClickedListener {
        public void sendMessage(String message);
    }
    
    
}
