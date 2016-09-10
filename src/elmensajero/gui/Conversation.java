
package elmensajero.gui;

import elmensajero.Contact;
import elmensajero.Message;
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
    
    private final ConversationLeftPane conversationLeftPane;
    private final ConversationMainPane conversationMainPane;
    
    /**
     * Construtor da classe Conversation.
     * Inicializa a classe adicionado a sua esquerda uma instancia da 
     * classe ConversationLeftPane e a sua direita uma instancia da
     * classe ConversationMainPane
     * 
     * @see elmensajero.gui.ConversationLeftPane
     * @see elmensajero.gui.ConversationMainPane
     * 
    */
    public Conversation() {
        conversationLeftPane = new ConversationLeftPane();
        conversationMainPane = new ConversationMainPane();
        this.setLeft( conversationLeftPane );
        this.setCenter( conversationMainPane );
    }
    
    /**
     * Adiciona uma mensagem na interface grafica.
     * Chama a classe inferior "dona" dessa parte
     * da interface grafica
     * 
     * @param message
     * @param contact
     */
    public void addMessage(Message message, Contact contact){
        conversationMainPane.addMessage( message, contact );
    }
    
    /**
     * Limpa as mensagens e adiciona as mensagens na interface grafica.
     * Chama a classe inferior "dona" dessa parte da interface grafica
     * 
     * @param messages
     * @param contact
     */
    public void setMessages(Message[] messages, Contact contact){
        conversationMainPane.setMessages( messages, contact );
    }
    
    /**
     * Define os componentes da interface grafica para o contato atual.
     * Chama a classe inferior "dona" dessa parte
     * da interface grafica
     * 
     * @param contact
     */
    public void setContact(Contact contact){
        conversationLeftPane.setContact(contact);
    }
    
}
