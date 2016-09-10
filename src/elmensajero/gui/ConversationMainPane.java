
package elmensajero.gui;

import elmensajero.Contact;
import elmensajero.Message;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * classe ConversationMainPane.
 * Classe define a principal parte da classe Conversation
 * Aqui situa a área para digitar a mensagem,  
 * o botao de envio (importados da classe ConversationMainPaneBottom)
 * e as mensagens trocadas pelos usuários.
 * 
 * @author Samuel
 * @see javafx.beans.value.ChangeListener
 */
public class ConversationMainPane extends BorderPane implements ChangeListener{
    
    private final ScrollPane scroll;
    private boolean fixScrollFlag;
    private VBox messages;
    
    /**
     * Construtor da classe ConversationMainPane.
     * Inicializa a classe adicionado definindo sua cor de fundo
     * Executa a função que gera as mensagens
     * Define as propriedade do Scroll das mensagens
     * Seta para o bottom a classe ConversationMainPaneBottom
     * Seta para o centro o Scroll e as mensagens
     * 
     * @see javafx.beans.value.ChangeListener
     * 
    */
    public ConversationMainPane() {
        fixScrollFlag = false;
        this.setStyle("-fx-background-color: white");
        
        initMessages();
        scroll = initScrollPane();
        
        this.setCenter(scroll);
        this.setBottom(new ConversationMainPaneBottom());        
    }

    /**
     * Metodo chamado pela interface.
     * Chamado quando ha alguma mudanca na altura do VBox
     * correspondente a lista das mensagens, usado para corrigir o 
     * scroll para deixar rolado o maximo para baixo se a conversa
     * foi aberta no momento ou entao se estava no mais abaixo possivel
     * e foi adicionada uma mensagem e para continuar o mais abaixo
     * 
     * @see javafx.beans.value.ChangeListener
     * 
     * @param observable
     * @param oldValue
     * @param newValue
     */
    @Override
    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        if ( fixScrollFlag ){
            scroll.setVvalue( 1d );
            fixScrollFlag = false;
        }
    }
    
    /**
     * Adiciona uma mensagem para a interface grafica.
     * Cria o elemento da interface grafica e o adiciona ao 
     * VBox das mensagens. Define tambem a flag de correcao
     * do scroll caso necessario
     * 
     * @see javafx.scene.layout.VBox
     * @see elmensajero.gui.MessageBox
     * @see elmensajero.Message
     * 
     * @param message
     * @param sender
     */
    public void addMessage(final Message message, final Contact contact){
        
        boolean sender = (message.getSender().equals(contact));
        
        final MessageBox box = new MessageBox(message,sender);
        Platform.runLater(() -> {
            messages.getChildren().add(box);
            if ( scroll.getVvalue() == 1d ){
                fixScrollFlag = true;
            }
               
        });

    }
    
    /**
     * Retira as mensagens existentes e adiciona varias mensagen
     * para a interface grafica.
     * Limpa a VBox das mensagens e cria os elementos da interface
     * grafica e os adiciona ao VBox das mensagens. Define tambem a
     * flag de correcao
     * 
     * @see javafx.scene.layout.VBox
     * @see elmensajero.gui.MessageBox
     * @see elmensajero.Message
     * 
     * @param message
     * @param contact
     */
    
    public void setMessages(final Message[] message, final Contact contact){
        
        final MessageBox[] boxes = new MessageBox[message.length];
        
        for(int i = 0; i < message.length; i++){
            boolean sender = ( message[i].getSender().equals(contact) );
            boxes[i] = new MessageBox( message[i], sender );
        }
        Platform.runLater(() -> {
            messages.getChildren().setAll(boxes);
            fixScrollFlag = true;
        });
    } 
    
     /**
     * Metodo chamado pelo Construtor.
     * Cria o ScrollPane utilizado para a rolagem das mensagens
     * Coloca a cor de plano de fundo do sroll
     */
    
    private ScrollPane initScrollPane(){
        ScrollPane scrollPane = new ScrollPane( messages );
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setEffect(new DropShadow(10, Color.BLACK)); 
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        return scrollPane;
    }
    
     /**
     * Metodo chamado pelo Construtor.
     * Cria um VBox e define suas propriedades
     */
    private void initMessages(){      
        messages = new VBox();
        messages.setSpacing(5);
        messages.paddingProperty().set(new Insets(50,20,15,20));
//        messages.setAlignment(Pos.BOTTOM_LEFT);
        messages.heightProperty().addListener( this );
    }
    
    
    
}
