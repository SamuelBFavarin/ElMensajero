
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
            //file.store(new BufferedOutputStream(new FileOutputStream(USERDATA_FILE)), "UserData");
        } catch (Exception e){
        }
    }
    public static Contact getUserData(){
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
        return c;
    }
    
}
