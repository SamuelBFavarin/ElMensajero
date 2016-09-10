
package elmensajero;

import java.util.Comparator;

/**
 *
 * @author Vinicius
 */
public class Contact {
    private String name, email, image;
    private Status status;

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
    
    public static class ContactComparator implements Comparator<Contact> {
        private ContactComparator(){}
        private static ContactComparator instance = null;
        public static ContactComparator getInstance(){
            if ( instance == null )
                instance = new ContactComparator();
            System.out.println("Come to me baby");
            return instance;
        }
        @Override
        public int compare(Contact a, Contact b) {
            if ( a.getStatus() != b.getStatus()  ){
                return ( a.getStatus() == Contact.Status.ONLINE ? -1:1 );
            }
            return a.getName().compareTo(b.getName());
        }
    }
    
}
