/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elmensajero.gui;

import elmensajero.Contact;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 * Campo de filtro de texto.
 * Extende TextField e implementa EventHandler e ListChangeListener
 * para ter maior facilidade ao criar os listeners de modificacao da dados
 * Tem a funcao tambem de gerar uma nova lista observavel filtrada
 * 
 * @see javafx.scene.control.TextField
 * 
 * @author Vinicius Santos
 */
public class FilterField extends TextField implements EventHandler<KeyEvent>, ListChangeListener<Contact> {

    private final ObservableList<Contact> contacts, filteredContacts;
    
    /**
     * construtor, define atributos de acordo com o parametro.
     * instancia tambem a lista observavel filtrada
     * chama a funcao de inicializar a interface do text field
     * 
     * @param contacts
     */
    public FilterField(ObservableList<Contact> contacts) {
        super();
        this.contacts = contacts;
        filteredContacts = FXCollections.observableArrayList( new ArrayList<>(contacts) );
        init();
    }
    
    /**
     * Metodo getter para obtencao da lista observavel filtrada;
     * @return lista observavel filtrada
     */
    public ObservableList<Contact> getFilteredList(){
        return filteredContacts;
    }
    
    /**
     * Inicializa componente de interface grafica.
     */
    private void init(){
        this.setPromptText("Buscar Contatos");
        this.setFocusTraversable(false);
        
        this.contacts.addListener(this);
        this.setOnKeyReleased(this);
    }
    
    /**
     * metodo que troca os valores da lista filtrada
     * de acordo com o texto escrito no campo de texto
     * 
     * @see javafx.collections.ObservableList
     */
    private void filter(){
        List<Contact> cs = new ArrayList<>();
        for (Contact c : contacts){
            if ( c.getName().contains( this.getText() ) ){
                cs.add(c);
            }
        }
        filteredContacts.setAll(cs);
    }

    /**
     * Metodo chamado automaticamente quando a lista observavel
     * no atributo contacts for modificada.
     * chama o metodo filter() para atualizar os dados na lista
     * observavel filtrada
     * 
     * @see javafx.collections.ListChangeListener
     * 
     * @param c
     */
    @Override
    public void onChanged(ListChangeListener.Change<? extends Contact> c) {
        filter();
    }
    
    /**
     * Metodo chamado automaticamente quando o campo de texto
     * ter chamado o listener de tecla solta.
     * chama o metodo filter() para atualizar os dados na lista
     * observavel filtrada
     * 
     * @see javafx.event.EventHandler
     * 
     * @param event
     */
    @Override
    public void handle(KeyEvent event) {
        filter();
    }

}
