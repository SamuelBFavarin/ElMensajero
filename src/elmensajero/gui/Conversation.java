
package elmensajero.gui;

import elmensajero.Message;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.scene.effect.DropShadow;


/**
 * classe Conversation
 * Gera toda a parte de interface gráfica da conversa
 *    -Contato do parceiro de comversa
 *    -Botões e áreas para envio de mensagem
 *    -Troca de mensagens
 * 
 * @author Samuel
 * @see javafx.beans.value.ChangeListener
 */
class Conversation extends BorderPane  {
    /**
     * Construtor da classe Conversation.
     * Inicializa a classe adicionado a sua esquerda uma instancia da 
     * classe ConversationLeftPane e a sua direita uma instancia da
     * classe ConversationMainPane
     * 
    */
    
    public Conversation() {
        this.setLeft(new ConversationLeftPane());  
        this.setCenter(new ConversationMainPane()); 
    }
}
