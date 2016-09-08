
package elmensajero.gui;

import javafx.scene.layout.BorderPane;

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
