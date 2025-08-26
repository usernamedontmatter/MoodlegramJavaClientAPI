package Auth;

import Client.error.UnknownAuthType;
import IOStream.IOStreamInterface;

public interface AuthInterface {
    static AuthInterface getAuthTypeByNumber(byte number) throws Exception {
        return switch(number) {
            case 1 -> new NoAuth();
            default -> throw new UnknownAuthType();
        };
    }

    long getID() throws Exception;

    void start(IOStreamInterface iostream) throws Exception;
    void authenticate(IOStreamInterface iostream) throws Exception;
}
