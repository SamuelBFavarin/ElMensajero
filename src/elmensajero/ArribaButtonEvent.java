
package elmensajero;

import javafx.event.Event;
import javafx.event.EventHandler;

/**
 * Evento do botão de Arriba (tremer a tela)
 * Futuras implementações
 * @author Samuel
 */
public class ArribaButtonEvent implements EventHandler{

    @Override
    public void handle(Event event) {
        System.out.println("VAI TREMER A TELA");
    }
    
}
