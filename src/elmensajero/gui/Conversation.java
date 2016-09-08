
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
 *
 * @author Samuel
 */
class Conversation extends BorderPane implements ChangeListener {
    
    private boolean fixScrollFlag;
    private ScrollPane scroll;
    private VBox messages;

    public Conversation() {
        
        fixScrollFlag = false;
        this.setLeft(initLeftBox());  
        this.setCenter(initMainBox());
        
        new Thread(new Runnable() {
            @Override public void run() {
                setMessages(new Message[]{
                    new Message(null, null, "OIIII", null),
                    new Message(null, null, "Gatinhooooooooooooooooooo!!!", null),
                    new Message(null, null, "Passa teu número", null),
                    new Message(null, null, "Quero nudes", null),
                    new Message(null, null, "Quero nudes", null),
                    new Message(null, null, "Quero nudes", null),
                    new Message(null, null, "Quero nudes", null),
                    new Message(null, null, "POR FAVOR", null),
                    new Message(null, null, "quer ver fotos nuas minhas ?", null),
                    new Message(null, null, "www.meguinhafoxnudesexmais18.com.br", null),
                    new Message(null, null, "To te esperando", null),
                    new Message(null, null, "Não vai acessar ?", null),
                    new Message(null, null, "teste", null),
                    new Message(null, null, "teste", null)
                });
                for (int i = 1; i < 15; i++){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {}
                    addMessage( new Message(null, null, "teste "+i, null) );
                }
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
        Platform.runLater(new Runnable() {
            @Override public void run() {
                messages.getChildren().add(box);
                if ( scroll.getVvalue() == 1d ){
                    fixScrollFlag = true;
                }
//                messages.setAlignment(Pos.TOP_RIGHT);
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
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                messages.getChildren().setAll(boxes);
                fixScrollFlag = true;
            }
        });
    }
    
    private Node initMainBox(){
        BorderPane mainbox = new BorderPane();
        mainbox.setStyle("-fx-background-color: #20B2AA;");
        
        Node bottomArea = initBottomArea();
      
        messages = new VBox();
        messages.setSpacing(5);
        messages.paddingProperty().set(new Insets(50,20,15,20));
        messages.setAlignment(Pos.BOTTOM_LEFT);   
        messages.heightProperty().addListener( this );
        
        scroll = initScrollPane();
        
        mainbox.setCenter(scroll);
        mainbox.setBottom(bottomArea);
        
        return mainbox;
    }
    
    private Node initBottomArea(){
        HBox hbox = new HBox();
        TextArea txtArea = initTextArea();
        Button sendButton = initSendButton();
        
        hbox.setMargin(txtArea,new Insets(50,20,15,20));
        hbox.setMargin(sendButton,new Insets(50,10,0,0));
        hbox.getChildren().add(txtArea);
        hbox.getChildren().add(sendButton);
        return hbox;
    }
    private ScrollPane initScrollPane(){
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(messages);
        scrollPane.setStyle("-fx-background-color: #20B2AA;");
//        scrollPane.setMaxHeight(500);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        return scrollPane;
    }
    
    private TextArea initTextArea(){
        TextArea txtArea = new TextArea();
        txtArea.setMaxHeight(125);
        txtArea.setWrapText(true);
        txtArea.setStyle("-fx-font-size: 20pt;");
        return txtArea; 
    }
    
    private Button initSendButton(){
        Button send = new Button("ENVIAR");
        send.setMinSize(125, 125);
        send.setTextFill(Color.web("#FFFFFF"));
        send.setStyle(
            "-fx-font-size: 20pt;"
          + "-fx-background-color:#008B8B;"
        );
        return send;
    }
    
    private Node initLeftBox(){
        VBox vbox = new VBox();
        Label friendName = initFriendName();
        ImageView friendImage = initFriendImage();
        vbox.setPrefWidth(200);
        vbox.setStyle("-fx-background-color: #008B8B;");
        vbox.setSpacing(2);
        vbox.getChildren().add(friendName);
        vbox.getChildren().add(friendImage);
               
        vbox.setMargin(friendName,new Insets(30,1,2,25));
        vbox.setMargin(friendImage,new Insets(0,1,30,20));
        
        return vbox;
    }
    
    private Label initFriendName(){
        Label txtName = new Label("Megan Fox");
        txtName.setTextFill(Color.web("#FFFFFF"));
        txtName.setFont(new Font("Arial",20));
        txtName.setEffect(new DropShadow(5, Color.WHITE));  
        return txtName;
    }
    
    private ImageView initFriendImage(){
        Image imgAvatar = new Image("http://sizlingpeople.com/wp-content/uploads/2016/05/Megan-Fox-Instagram.jpg");
        ImageView avatar = new ImageView(imgAvatar);
        avatar.setFitWidth(150);
        avatar.setPreserveRatio(true);
        avatar.setSmooth(true);
        avatar.setCache(true);
        avatar.setEffect(new DropShadow(15, Color.BLACK));
        return avatar;
    }

//    private void setOnSelectionChanged(EventHandler<Event> eventHandler) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
    
}
