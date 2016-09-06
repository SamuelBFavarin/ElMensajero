
package elmensajero;

import elmensajero.gui.ElMensajeroGUI;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author Vinicius
 */
public class ElMensajero extends Application {

    private ElMensajeroGUI gui;
    private List<Contact> contatos;

    /**
     *
     */
    public ElMensajero() {
        contatos = new ArrayList<>();
        contatos.add(new Contact("Vinícius", "vinicius@rudinei.cnt.br", "me.png"));
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        gui = new ElMensajeroGUI( stage, contatos );
    }
    
    public static void main(String[] args){
        launch(args);
    }
}
