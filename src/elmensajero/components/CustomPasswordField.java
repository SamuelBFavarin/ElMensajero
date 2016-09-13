package elmensajero.components;

import elmensajero.validators.Validator;
import javafx.scene.control.PasswordField;

/**
 *
 * @author Lucas Baragatti
 */
public class CustomPasswordField extends PasswordField {

    private Validator validator;

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public boolean isValueValid() {
        return (validator != null) ? validator.isValueValid(getText()) : true;
    }
}
