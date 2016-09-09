
package elmensajero.gui;

import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

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
     * classe ConversationTopPane e a sua direita uma instancia da
     * classe ConversationMainPane
     * Define uma sombra
     * 
    */
    
    public Conversation() {
        this.setEffect(new DropShadow(5, Color.BLACK));  
        this.setTop(new ConversationTopPane());  
        this.setCenter(new ConversationMainPane()); 
    }
}
