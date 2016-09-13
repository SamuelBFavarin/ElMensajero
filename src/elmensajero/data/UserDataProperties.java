package elmensajero.data;

import elmensajero.data.base.ContactDB;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.util.Formatter;
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
    
    public static String encode(String password) throws Exception{
        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
        crypt.reset();
        crypt.update(password.getBytes("UTF-8"));
        Formatter formatter = new Formatter();
        for (byte b : crypt.digest())
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
    
    
    public static void deleteUserData(){
        new File(USERDATA_FILE).delete();
    }
    
    public static void setUserData(ContactDB c){
        try {
            Properties file = getInstance();
            file.setProperty("email", c.getEmail());
            file.setProperty("pasword", c.getPassword());
            file.store(new BufferedOutputStream(new FileOutputStream(USERDATA_FILE)), "UserData");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public static ContactDB getUserData(){
        try {
            Properties file = getInstance();
            file.load(new BufferedInputStream(new FileInputStream(USERDATA_FILE)));
            return new ContactDB(
                null,
                file.getProperty("email"),
                file.getProperty("password"),
                null
            );
        } catch (Exception e){
        }
        return null;
    }
    
}
