
package elmensajero;

/**
 *
 * @author Vinicius
 */
public class Contact {
    protected String name, email, image;
    protected Status status;

    public enum Status { ONLINE, OFFLINE; }
    
    /**
     *
     * @author Vinicius
     * @param name
     * @param email
     * @param image
     */
    public Contact(String name, String email, String image, Status status) {
        this.name = name;
        this.email = email;
        this.image = image;
        this.status = status;
    }

    public Contact() {
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

    public Status getStatus() {
        return status;
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

    public void setStatus(Status status) {
        this.status = status;
    }
    
}
