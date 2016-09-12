
package elmensajero.data.base;

import elmensajero.Contact;

/**
 *
 * @author Samuel
 */
public class ContactsDB extends Contact{
    private String password;
    
    public ContactsDB( String name, String email, String senha) {
        this.password = password;
        super.name = name;
        super.email = email;
    }

    public String getPassword() {
        return password;
    }
    
    
}
