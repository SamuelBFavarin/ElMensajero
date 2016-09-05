
package elmensajero.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author Vinicius
 */
public class ElMensajeroGUI extends Application {

    public ElMensajeroGUI(){}
    
    public ElMensajeroGUI(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) {
                
        BorderPane root = new BorderPane();
        root.setLeft(new Friends());
        root.setCenter(new Conversation());
        
        Scene scene = new Scene(root, 300, 250);
        stage.setTitle("El Mensagero");
        stage.setScene(scene);
        stage.show();
    }
    
}
