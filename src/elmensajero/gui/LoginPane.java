package elmensajero.gui;

import elmensajero.components.CustomPasswordField;
import elmensajero.components.CustomTextField;
import elmensajero.validators.EmailValidator;
import elmensajero.validators.SimpleValidator;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 *
 * @author Vinicius
 */
public class LoginPane extends GridPane {

    private CustomTextField email;
    private CustomPasswordField password;
    private Button register;

    private LoginGUI.LoginListener loginListener;

    public LoginPane() {
        loginListener = null;
        init();
    }

    private void init() {

        this.setHgap(20);
        this.setVgap(20);
        this.setAlignment(Pos.CENTER);

        this.add(initTitle(), 0, 0, 2, 1);

        this.add(new Label("E-Mail:"), 0, 1);
        this.add(new Label("Senha:"), 0, 2);

        email = new CustomTextField();
        password = new CustomPasswordField();

        SimpleValidator passwordValidator = new SimpleValidator("Senha", true);
        passwordValidator.setMinLength(6);
        
        email.setValidator(new EmailValidator("E-mail", true));
        password.setValidator(passwordValidator);

        this.add(email, 1, 1);
        this.add(password, 1, 2);

        this.add(initButtons(), 0, 3, 2, 1);
    }

    private Node initTitle() {
        Label title = new Label("Login El Mensajero");
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 22));
        return title;
    }

    private TextField initField(boolean password) {
        if (password) {
            return new PasswordField();
        }
        return new TextField();
    }

    private Node initButtons() {
        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER_RIGHT);

        register = initButton("Cadastrar-se");
        Button cancel = initButton("Cancelar");
        Button login = initButton("Entrar");

        buttons.getChildren().add(register);
        buttons.getChildren().add(cancel);
        buttons.getChildren().add(login);

        cancel.setOnMouseClicked(cancelListener());
        login.setOnMouseClicked(loginButtonListener(login, cancel));
        return buttons;
    }

    private Button initButton(String text) {
        Button btn = new Button(text);
        btn.setAlignment(Pos.BASELINE_RIGHT);
        return btn;
    }

    private EventHandler<MouseEvent> cancelListener() {
        return (MouseEvent event) -> {
            System.exit(0);
        };
    }

    private EventHandler<MouseEvent> loginButtonListener(Button loginButton, Button cancelButton) {
        return (MouseEvent event) -> {
            if (loginListener == null) {
                return;
            }

            if (!email.isValueValid() || !password.isValueValid()) {
                return;
            }

            email.setEditable(false);
            password.setEditable(false);
            loginButton.setDisable(true);
            cancelButton.setDisable(true);

            loginListener.tryLogin(email.getText(), password.getText());

            email.setEditable(true);
            password.setEditable(true);
            loginButton.setDisable(false);
            cancelButton.setDisable(false);
        };
    }

    public void setRegisterButtonClickedListener(EventHandler<MouseEvent> listener) {
        register.setOnMouseClicked(listener);
    }

    public void setLoginListener(LoginGUI.LoginListener loginListener) {
        this.loginListener = loginListener;
    }

}
