
package elmensajero.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Vinicius
 */
public class ElMensajeroGUI extends Application {

    public ElMensajeroGUI() {
        launch((String[]) null);
    }
    
    @Override
    public void start(Stage stage) {
        
        
        BorderPane root = new BorderPane();
        root.setLeft(new Friends());
        
        Scene scene = new Scene(root, 300, 250);
        
        stage.setTitle("El Mensagero");
        stage.setScene(scene);
        stage.show();
    }
    
}
