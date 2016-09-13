package elmensajero.gui;

import elmensajero.components.CustomPasswordField;
import elmensajero.components.CustomTextField;
import elmensajero.validators.EmailValidator;
import elmensajero.validators.SimpleValidator;
import java.io.File;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Vinicius
 */
public class RegisterPane extends GridPane {

    private CustomTextField name, email, image;
    private CustomPasswordField password;
    private File imageFile;

    private final Stage stage;

    private Button backToLogin;

    private LoginGUI.LoginListener loginListener;

    public RegisterPane(Stage stage) {
        this.stage = stage;
        loginListener = null;
        init();
    }

    private void init() {

        this.setHgap(20);
        this.setVgap(20);
        this.setAlignment(Pos.CENTER);

        this.add(initTitle(), 0, 0, 2, 1);

        this.add(new Label("Nome:"), 0, 1);
        this.add(new Label("E-Mail:"), 0, 2);
        this.add(new Label("Senha:"), 0, 3);
        this.add(new Label("Imagem:"), 0, 4);

        name = new CustomTextField();
        email = new CustomTextField();
        password = new CustomPasswordField();
        image = new CustomTextField();
        Button btnImage = new Button("Selecionar");

        SimpleValidator passwordValidator = new SimpleValidator("Senha", true);
        passwordValidator.setMinLength(6);

        name.setValidator(new SimpleValidator("Nome", true));
        email.setValidator(new EmailValidator("E-mail", true));
        password.setValidator(passwordValidator);
        image.setValidator(new SimpleValidator("Imagem", true));

        image.setEditable(false);

        HBox imageChooser = new HBox(1, image, btnImage);

        btnImage.setOnMouseClicked((MouseEvent e) -> {
            File file = chooseImage();
            this.imageFile = file;
            if (file != null) {
                image.setText(file.getName());
            } else {
                image.setText("");
            }
        });

        this.add(name, 1, 1);
        this.add(email, 1, 2);
        this.add(password, 1, 3);
        this.add(imageChooser, 1, 4);

        this.add(initButtons(), 0, 5, 2, 1);
    }

    private Node initTitle() {
        Label title = new Label("Cadastro El Mensajero");
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 22));
        return title;
    }

    private Node initButtons() {
        HBox buttons = new HBox(5);
        buttons.setAlignment(Pos.CENTER_RIGHT);

        backToLogin = initButton("Voltar");
        Button cancel = initButton("Cancelar");
        Button register = initButton("Cadastrar-se");

        buttons.getChildren().add(backToLogin);
        buttons.getChildren().add(cancel);
        buttons.getChildren().add(register);

        cancel.setOnMouseClicked(cancelListener());
        register.setOnMouseClicked(registerButtonListener());
        return buttons;
    }

    private Button initButton(String text) {
        Button btn = new Button(text);
        btn.setAlignment(Pos.BASELINE_RIGHT);
        return btn;
    }

    private File chooseImage() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Escolha uma imagem");
        chooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Imagens", "*.png;*.jpg;*.jpeg")
        );
        return chooser.showOpenDialog(stage);
    }

    private EventHandler<MouseEvent> cancelListener() {
        return (MouseEvent event) -> {
            System.exit(0);
        };
    }

    private EventHandler<MouseEvent> registerButtonListener() {
        return (MouseEvent event) -> {
            if (loginListener == null) {
                return;
            }

            if (!name.isValueValid() || !email.isValueValid()
                    || !password.isValueValid() || !image.isValueValid()) {
                return;
            }

            ProgressIndicator progress = new ProgressIndicator(0);
            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.initStyle(StageStyle.UTILITY);
            alert.setHeaderText("Enviando imagem");
            alert.setContentText("Aguarde um momento");
            alert.setGraphic(progress);
            alert.show();
            new Thread(() -> {
                
                loginListener.tryRegister(name.getText(), email.getText(), password.getText(), imageFile, progress);
                
                Platform.runLater(() -> {
                    alert.getButtonTypes().setAll(ButtonType.OK);
                    alert.close();
                });
                
            }).start();
        };
    }

    public void setBackButtonClickedListener(EventHandler<MouseEvent> listener) {
        backToLogin.setOnMouseClicked(listener);
    }

    public void setLoginListener(LoginGUI.LoginListener loginListener) {
        this.loginListener = loginListener;
    }

}
