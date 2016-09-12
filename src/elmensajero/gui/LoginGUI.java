package elmensajero.gui;

import java.io.File;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Vinicius
 * @see javafx.scene.Scene
 * 
 */
public class LoginGUI extends StackPane {

    private static final double WIDTH = 350, HEIGHT = 320;
    private final Scene scene;
    private final Stage stage;
    
    private final LoginPane loginPane;
    private final RegisterPane registerPane;
    
    public LoginGUI(Stage stage) {
        super();
        
        this.setStyle(
            "-fx-focus-color: transparent;"
           +"-fx-background-color: #F0F0F0"
        );
        
        loginPane = new LoginPane();
        registerPane = new RegisterPane(stage);
        this.getChildren().setAll(loginPane);
        
        this.stage = stage;
        this.scene = new Scene( this, WIDTH, HEIGHT );
        
        loginPane.setRegisterButtonClickedListener((MouseEvent m) -> {
            this.getChildren().setAll(registerPane);
        });
        registerPane.setBackButtonClickedListener((MouseEvent m) -> {
            this.getChildren().setAll(loginPane);
        });
    }
    
    public void setLoginListener(LoginListener loginListener){
        loginPane.setLoginListener(loginListener);
        registerPane.setLoginListener(loginListener);
    }
    
    public interface LoginListener {
        public void tryLogin(String email, String password);
        public void tryRegister(String name,String email,String password,File image );
    }
    
    public void showError(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initStyle(StageStyle.UTILITY);
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
            stage.setTitle("Login El Mensajero");
            stage.setMinHeight(HEIGHT);
            stage.setMinWidth(WIDTH);
            stage.setScene(scene);
            stage.show();
        });
    }
    
    
}

