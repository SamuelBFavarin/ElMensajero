package elmensajero.components;

import elmensajero.validators.Validator;
import javafx.scene.control.TextField;

/**
 *
 * @author Lucas Baragatti
 */
public class CustomTextField extends TextField {

    private Validator validator;

    public CustomTextField() {
    }

    public CustomTextField(String text) {
        super(text);
    }
    
    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public boolean isValueValid() {
        return (validator != null) ? validator.isValueValid(getText()) : true;
    }
}
