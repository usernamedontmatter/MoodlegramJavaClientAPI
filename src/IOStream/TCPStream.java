package IOStream;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TCPStream implements IOStreamInterface {
    private final String address;
    private final int port;
    private Socket socket;
    public TCPStream(String address, int port) {
        this.address = address;
        this.port = port;
    }
    public void openStream() throws Exception {
        socket = new Socket(address, port);
    }
    public void closeStream() throws Exception {
        socket.close();
    }
    public InputStream getInputStream() throws Exception {
        return socket.getInputStream();
    }
    public OutputStream getOutputStream() throws Exception {
        return socket.getOutputStream();
    }
}