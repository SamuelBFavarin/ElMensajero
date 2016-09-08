
package elmensajero.gui;

import elmensajero.Message;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
    
    private boolean fixScrollFlag;
    private VBox messages;
    private ScrollPane scroll;
    
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
        this.setStyle("-fx-background-color: #20B2AA;");
        
        initMessages();
        scroll = initScrollPane();
        
        this.setCenter(scroll);
        this.setBottom(new ConversationMainPaneBottom());
        
        new Thread(() -> {
            setMessages(new Message[]{
                new Message(null, null, "OIIII", null),
                new Message(null, null, "Gatinho!!!", null),
            });
            for (int i = 1; i < 20; i++){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {}
                addMessage( new Message(null, null, "teste "+i, null) );
            }
        }).start();
        
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
     */
    public void addMessage(final Message message){
        
        final MessageBox box = new MessageBox(message,true);
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
     */
    
    public void setMessages(final Message[] message){
        
        final MessageBox[] boxes = new MessageBox[message.length];
        
        for(int i = 0; i < message.length; i++){
            boxes[i] = new MessageBox( message[i], false );
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
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(messages);
        scrollPane.setStyle("-fx-background-color: #FFFFFF;");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
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
        messages.setAlignment(Pos.BOTTOM_LEFT);  
        messages.heightProperty().addListener( this );
    }
}
