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
    
    public static class ConnectionType{
        private ConnectionType() {}
        public static final byte LISTENER = 0x01;
        public static final byte REQUEST  = 0x02;
    }
    
    public static final byte READY_TO_RECEIVE = 0x03;
    
    
    
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
