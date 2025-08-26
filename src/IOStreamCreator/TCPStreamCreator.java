package IOStreamCreator;

import IOStream.IOStreamInterface;
import IOStream.TCPStream;

public class TCPStreamCreator implements IOStreamCreatorInterface {
    private final String address;
    private final int port;

    public TCPStreamCreator(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public IOStreamInterface getStream() {
        return new TCPStream(address, port);
    }
}
