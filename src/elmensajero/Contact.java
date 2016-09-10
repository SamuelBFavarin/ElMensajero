
package elmensajero;

import java.io.Serializable;
import java.util.Comparator;

/**
 *
 * @author Vinicius
 */
public class Contact implements Serializable {
    private String name, email, image;
    private Status status;

    public enum Status { ONLINE, OFFLINE; }

    public Contact() {
    }
    
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
    
    /**
     * Comparacao entre Contatos.
     * Utiliza o email para comparar se o contato e o mesmo
     * @param contact
     * @return igualdade
     */
    public boolean equals(Contact contact){
        return this.getEmail().equals(contact.getEmail());
    }
    
    public static class ContactComparator implements Comparator<Contact> {
        private ContactComparator(){}
        private static ContactComparator instance = null;
        public static ContactComparator getInstance(){
            if ( instance == null )
                instance = new ContactComparator();
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
