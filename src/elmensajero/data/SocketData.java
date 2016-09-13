package elmensajero.data;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author Vinicius
 */
public class SocketData {
    private SocketData() {}
    
    public static final String   HOST = "31.220.22.185";
    public static final int      PORT = 3043;
    public static final int HTTP_PORT = 8080;
    
    public static class ConnectionType{
        private ConnectionType() {}
        public static final byte LISTENER = 0x01;
        public static final byte REQUEST  = 0x02;
    }
    
    public static final byte READY_TO_RECEIVE = 0x03;
    public static final byte IS_ALIVE         = 0x04;
    
    public static boolean testConnection(Socket client){
        try {
            client.setSoTimeout(1000);
            writeByte(client, SocketData.IS_ALIVE);
            byte nextByte = nextByte(client);
            client.setSoTimeout(0);
            return ( nextByte == SocketData.IS_ALIVE );
        } catch (Exception e){
            try{
                client.close();
            } catch ( Exception ex ){ }
            return false;
        }
    }
    
    public static void writeObject(Socket socket, Object data) throws Exception{
        ObjectOutputStream oos = new ObjectOutputStream( socket.getOutputStream() );
        if ( SocketData.nextByte(socket) == SocketData.READY_TO_RECEIVE ){
            oos.writeObject(data);
        } else throw new Exception("Algo deu errado");
    }
    
    public static Object readObject(Socket socket) throws Exception {
        writeByte(socket, SocketData.READY_TO_RECEIVE);
        ObjectInputStream ois = new ObjectInputStream( socket.getInputStream() );
        return ois.readObject();
    }
    
    public static void writeByte(Socket socket, byte aByte) throws Exception {
        OutputStream out = socket.getOutputStream();
        out.write( aByte );
    }
    
    public static byte nextByte(Socket socket) throws Exception{
        InputStream in = socket.getInputStream();
        int readed = in.read();
        if ( readed == -1 ){
            throw new Exception("Fim da conexão");
        }
        return (byte) readed;
    }
    
    
}
