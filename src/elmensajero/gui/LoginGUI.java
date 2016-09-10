
package elmensajero.gui;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 *
 * @author Vinicius
 * @see javafx.scene.Scene
 * 
 */
public class LoginGUI extends StackPane {

    private static final double WIDTH = 300, HEIGHT = 250;
    private final Scene scene;
    private final Stage stage;

    private final TextField email, password;
    
    private LoginListener loginListener;
    
    public LoginGUI(Stage stage) {
        super();
        loginListener = null;
        
        GridPane fields = new GridPane();
        
            fields.setHgap(20);
            fields.setVgap(20);
            fields.setAlignment(Pos.CENTER);
            
            Label titulo = new Label("Login El Mensajero");
                titulo.setFont(Font.font("Tahoma", FontWeight.NORMAL, 22));
            fields.add(titulo, 0, 0, 2, 1);
            
            fields.add( new Label("E-Mail:"), 0, 1 );
            fields.add( new Label("Senha:"),  0, 2 );
            
            email    = new TextField();
            fields.add( email, 1, 1 );
            
            password = new PasswordField();
            fields.add( password, 1, 2 );
            
            HBox buttons = new HBox(10);
                buttons.setAlignment(Pos.CENTER_RIGHT);

                Button cancelButton = new Button("Cancelar");
                cancelButton.setAlignment(Pos.BASELINE_RIGHT);
                buttons.getChildren().add(cancelButton);

                Button loginButton = new Button("Entrar");
                loginButton.setAlignment(Pos.BASELINE_RIGHT);
                buttons.getChildren().add(loginButton);
            fields.add(buttons, 0, 3, 2, 1);
                
        this.getChildren().add(fields);
        
        this.setStyle("-fx-focus-color: transparent");
        
        cancelButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
            System.exit(0);
        });
        
        loginButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
            if ( loginListener == null ) return;
            email.setEditable(false);
            password.setEditable(false);
            loginButton.setDisable(true);
            cancelButton.setDisable(true);
            
            loginListener.tryLogin( email.getText(), password.getText() );
            
            email.setEditable(true);
            password.setEditable(true);
            loginButton.setDisable(false);
            cancelButton.setDisable(false);
        });
        
        this.scene = new Scene(this, WIDTH, HEIGHT);
        this.stage = stage;        
    }
    
    public void setLoginListener(LoginListener loginListener){
        this.loginListener = loginListener;
    }
    
    public interface LoginListener {
        public void tryLogin(String email, String password);
    }
    
    public void showError(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Atenção");
        alert.setHeaderText("O que você fez de errado?");
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    /**
     * Define a classe como o scene da stage e mostra o stage.
     * 
     * @see javafx.stage.Stage
     * @see javafx.scene.Scene
     */
    public void show(){
        Platform.runLater(() -> {
            email.setText("");
            password.setText("");
            stage.setTitle("Login El Mensajero");
            stage.setMinHeight(HEIGHT);
            stage.setMinWidth(WIDTH);
            stage.setScene(scene);
            stage.show();
        });
    }
    
    
}

