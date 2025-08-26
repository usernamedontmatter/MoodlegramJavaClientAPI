package EncryptingLayout;

import IOStream.IOStreamInterface;

import java.io.InputStream;
import java.io.OutputStream;

public class NoEncryptingLayout implements EncryptingLayoutInterface {
    private IOStreamInterface iostream;

    public void openStream() {}
    public void closeStream() {}
    public InputStream getInputStream() throws Exception {
        return iostream.getInputStream();
    }
    public OutputStream getOutputStream() throws Exception {
        return iostream.getOutputStream();
    }

    public short getEncryptingNumber() {
        return 1;
    }

    public void setIOStream(IOStreamInterface iostream) {
        this.iostream = iostream;
    }
}
