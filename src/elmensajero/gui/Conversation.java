
package elmensajero.gui;

import elmensajero.Contact;
import elmensajero.Message;
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
    
    private final ConversationTopPane conversationTopPane;
    private final ConversationMainPane conversationMainPane;
    
    /**
     * Construtor da classe Conversation.
     * Inicializa a classe adicionado a sua esquerda uma instancia da 
     * classe ConversationTopPane e a sua direita uma instancia da
     * classe ConversationMainPane
     * Define uma sombra
     * 
     * @see elmensajero.gui.ConversationTopPane
     * @see elmensajero.gui.ConversationMainPane
     * 
    */
    public Conversation() {
        conversationTopPane = new ConversationTopPane();
        conversationMainPane = new ConversationMainPane();
        this.setEffect(new DropShadow(5, Color.BLACK));  
        this.setTop( conversationTopPane );  
        this.setCenter( conversationMainPane );
    }
    
    /**
     * Define o listener de clique do botao de enviar mensagem.
     * 
     * @see elmensajero.gui.ConversationMainPaneBottom.SendButtonClickedListener
     * 
     * @param listener
     */
    public void setSendButtonClickedListener(ConversationMainPaneBottom.SendButtonClickedListener listener){
        conversationMainPane.setSendButtonClickedListener( listener );
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
        conversationTopPane.setContact(contact);
    }
    
}
