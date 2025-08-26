package IOStream;

import java.io.InputStream;
import java.io.OutputStream;

public interface IOStreamInterface {
    void openStream() throws Exception;
    void closeStream() throws Exception;
    InputStream getInputStream() throws Exception;
    OutputStream getOutputStream() throws Exception;
}