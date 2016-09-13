package elmensajero.validators;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.StageStyle;

/**
 *
 * @author Lucas Baragatti
 */
public abstract class Validator {

    private String name;
    private boolean required;
    private int minLength = 0;

    public Validator(String name, boolean required) {
        this.name = name;
        this.required = required;
    }

    public Validator(String name) {
        this(name, false);
    }

    protected void showError(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Atenção");
        alert.setHeaderText("Erro de validação!");
        alert.setContentText(error);
        alert.showAndWait();
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    public boolean isValueValid(String value) {
        if (required && value.isEmpty()) {
            showError("O campo '" + name + "' é obrigatório!");
            return false;
        } else {
            if (value.length() < minLength) {
                showError("O tamanho mínimo do campo '" + name + "' é de " + minLength + " caracteres!");
                return false;
            }
            return validate(value);
        }
    }

    protected abstract boolean validate(String value);
}
