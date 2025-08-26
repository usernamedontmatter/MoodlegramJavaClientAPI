package Auth;

import Auth.error.AuthHasNotStarted;
import IOStream.IOStreamInterface;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class NoAuth implements AuthInterface {
    private long id;
    private boolean is_started = false;

    public long getID() throws Exception{
        if(!is_started) throw new AuthHasNotStarted();

        return id;
    }

    public void start(IOStreamInterface iostream) throws Exception {
        ByteBuffer buffer = ByteBuffer.wrap(iostream.getInputStream().readNBytes(8));
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        this.id = buffer.getLong();

        is_started = true;
    }
    public void authenticate(IOStreamInterface iostream) throws Exception {
        if(!is_started) throw new AuthHasNotStarted();

        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.putLong(id);

        iostream.getOutputStream().write(buffer.array());
    }
}
