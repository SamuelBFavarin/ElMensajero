/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elmensajero;

import elmensajero.gui.ElMensajeroGUI;

/**
 *
 * @author Vinicius
 */
public class ElMensajero {

    public ElMensajero(String[] args) {
        ElMensajeroGUI gui = new ElMensajeroGUI(args);
    }
    public static void main(String[] args){
        new ElMensajero(args);
    }
}