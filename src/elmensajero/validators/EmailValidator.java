package elmensajero.validators;

/**
 *
 * @author Lucas Baragatti
 */
public class EmailValidator extends Validator {

    public EmailValidator(String name, boolean required) {
        super(name, required);
    }

    @Override
    protected boolean validate(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        boolean resultado = m.matches();

        if (resultado == false) {
            showError("E-mail inválido!");
        }

        return resultado;
    }

}
