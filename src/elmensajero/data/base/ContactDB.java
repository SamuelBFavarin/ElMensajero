
package elmensajero.data.base;

import elmensajero.Contact;

/**
 *
 * @author Samuel
 */
public class ContactDB extends Contact{
    private String password;
    
    public ContactDB( String name, String email, String password, String image) {
        this.password = password;
        super.name = name;
        super.email = email;
        super.image = image;
    }

    public String getPassword() {
        return password;
    }
    
    
}
