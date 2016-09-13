
package elmensajero.data.base;

import elmensajero.Contact;

/**
 *
 * @author Samuel
 */
public class ContactDB extends Contact{
    private String password;
    
    public ContactDB( String name, String email, String password, String image) {
        super(name, email, image, Status.OFFLINE);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
    
    
}
