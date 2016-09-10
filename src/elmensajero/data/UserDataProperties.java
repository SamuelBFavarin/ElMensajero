
package elmensajero.data;

import elmensajero.Contact;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 *
 * @author Vinicius
 */
public class UserDataProperties extends Properties {

    private static final String USERDATA_FILE = "./userdata.properties";
    private static UserDataProperties instance;
    
    private static UserDataProperties getInstance(){
        if ( instance == null ) {
            instance = new UserDataProperties();
        }
        return instance;
    }
    
    private UserDataProperties() {
        super();
    }
    
    public static void setUserData(Contact c){
        try {
            Properties file = getInstance();
            file.setProperty("name", c.getName());
            file.setProperty("email", c.getEmail());
            file.setProperty("image", c.getImage());
            file.store(new BufferedOutputStream(new FileOutputStream(USERDATA_FILE)), "UserData");
        } catch (Exception e){
        }
    }
    public static Contact getUserData(){
        return new Contact(
            "Vinícius",
            "vinicius@rudinei.cnt.br",
            "https://fbcdn-sphotos-g-a.akamaihd.net/hphotos-ak-frc1/v/t1.0-9/10959463_759249350825529_7123328862024803112_n.jpg?oh=8d0fe4f644fd3ee33deafd3840049e62&oe=5846BB67&__gda__=1480345353_1e4d5e27b1e477383421a05b5bffa27c",
            Contact.Status.ONLINE
        );/*
        Contact c = new Contact();
        try {
            Properties file = getInstance();
            file.load(new BufferedInputStream(new FileInputStream(USERDATA_FILE)));
            c.setName ( file.getProperty("name")  );
            c.setEmail( file.getProperty("email") );
            c.setImage( file.getProperty("image") );
        } catch (Exception e){
            return null;
        }
        return c;*/
    }
    
}
