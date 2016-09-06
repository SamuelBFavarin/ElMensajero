
package elmensajero;

/**
 *
 * @author Vinicius
 */
public class Contact {
    private String name, email, image;
    private int status;
    /**
     *
     * @author Vinicius
     * @param name
     * @param email
     * @param image
     */
    public Contact(String name, String email, String image) {
        this.name = name;
        this.email = email;
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    
    
}
