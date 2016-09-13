package elmensajero.validators;

/**
 *
 * @author Lucas Baragatti
 */
public class SimpleValidator extends Validator {

    public SimpleValidator(String name, boolean required) {
        super(name, required);
    }

    @Override
    protected boolean validate(String value) {
        return true;
    }

}
