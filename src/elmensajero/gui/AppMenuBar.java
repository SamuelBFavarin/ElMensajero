package elmensajero.gui;

import elmensajero.ElMensajero;
import elmensajero.data.RetrieveDataListener;
import elmensajero.data.UserDataProperties;
import elmensajero.data.base.ContactDB;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Vinicius
 */
public class AppMenuBar extends MenuBar implements LoginGUI.LoginListener{

    private final Stage stage;
    private final LoginGUI loginGUI;
    private final ElMensajeroGUI mainGUI;
    private final RetrieveDataListener retrieveDataListener;
    private RegisterPane registerPane;
    private ContactDB userData;
    
    private MenuItem dataEdit, logout;
    private Alert editAlert;
    
    public AppMenuBar(Stage stage, RetrieveDataListener retrieveDataListener, LoginGUI loginGUI, ElMensajeroGUI mainGUI) {
        this.stage = stage;
        this.mainGUI = mainGUI;
        this.loginGUI = loginGUI;
        this.retrieveDataListener = retrieveDataListener;
        init();
        initListeners();
        initEditAlert();
    }
    
    public void setUserData(ContactDB userData){
        this.userData = userData;
    }
    
    private void init(){
        this.setStyle(
            "-fx-background-color:#EFEFEF;"
           +"-fx-border-color: #777777;"
           +"-fx-border-width: 0 0 1px 0;"
        );
        
        Menu options = new Menu("Opções");
        
        dataEdit = new MenuItem("Editar dados");
        logout = new MenuItem("Sair");
        
        options.getItems().addAll( dataEdit, logout );
        
        getMenus().addAll( options );
    }
    
    private void initListeners(){
        dataEdit.setOnAction( (ActionEvent event) -> dataEdit() );
        logout.setOnAction( (ActionEvent event) -> logout() );
    }
    
    private void initEditAlert(){
        editAlert = new Alert(Alert.AlertType.NONE);
        editAlert.initStyle(StageStyle.UTILITY);
        editAlert.setTitle("Edição dos dados de usuário");
        registerPane = new RegisterPane(stage, "Edição de dados", true);
        registerPane.setLoginListener(this);
        registerPane.setBackButtonClickedListener((MouseEvent l) -> {
            editAlert.getButtonTypes().add(ButtonType.OK);
            editAlert.close();
            editAlert.getButtonTypes().setAll();
        });
        editAlert.getDialogPane().setContent(registerPane);
        editAlert.getDialogPane().setExpanded(true);
    }
    
    private void dataEdit(){
        registerPane.setUserData(userData);
        editAlert.showAndWait();
    }
    
    private void logout(){
        UserDataProperties.deleteUserData();
        loginGUI.show();
    }

    @Override
    public void tryLogin(String email, String password) {}

    @Override
    public void tryRegister(String name, String email, String password, File imageFile, ProgressIndicator progress) {
        if ( name.isEmpty() || email.isEmpty() ){
                loginGUI.showError("Preencha todos os campos");
                return;
            }
            String image = userData.getImage();
            if ( imageFile != null ){
                image = retrieveDataListener.sendFile(imageFile, progress);
                if ( image == null ){
                    loginGUI.showError("Erro ao enviar imagem");
                    return;
                }
            }
            try {
                if ( password.isEmpty() ){
                    password = userData.getPassword();
                } else {
                    password = UserDataProperties.encode(password);
                }
                ContactDB user = new ContactDB(name, email, password, image);
                boolean res = retrieveDataListener.editUser(user);
                if ( res ){
                    UserDataProperties.setUserData(user);
                    Platform.runLater(() -> {
                        editAlert.getButtonTypes().add(ButtonType.OK);
                        editAlert.close();
                        editAlert.getButtonTypes().setAll();
                        mainGUI.setUserData(user);
                    });
                } else {
                    loginGUI.showError("Erro na alteração");
                }
            } catch (Exception ex) {
                Logger.getLogger(ElMensajero.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
}
