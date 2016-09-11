/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elmensajero;

import java.util.Date;
import javafx.event.Event;
import javafx.event.EventHandler;

/**
 * Evento do botão de enviar mensagem
 * Futuras implementações
 * @author Samuel
 */
public class SendButtonEvent implements EventHandler{
    
    
    @Override
    public void handle(Event event) {
        System.out.println("BOTÃO CLICLADO");
    }

    
}
