package Client;

import Auth.AuthInterface;
import EncryptingLayout.EncryptingLayoutInterface;
import IOStream.IOStreamInterface;
import Client.error.*;
import IOStreamCreator.IOStreamCreatorInterface;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public class Client {
    // Private variables
    private final byte version = 1;
    private enum command_type {
        registration((byte)1),
        send_message((byte)2),
        get_messages((byte)3);

        public final byte value;

        command_type(byte value) {
            this.value = value;
        }
    }

    private final IOStreamCreatorInterface iostream_creator;
    private AuthInterface auth;

    private boolean is_registered = false;

    // Public functions
    public long getID() throws Exception{
        return auth.getID();
    }

    public Client(IOStreamCreatorInterface iostream_creator) {
        this.iostream_creator = iostream_creator;
    }

    public <T extends EncryptingLayoutInterface> void register(Class<T> encrypting_class) throws Exception {
        if(is_registered) throw new ClientAlreadyRegisteredError();

        T encrypting_layout = encrypting_class.getConstructor().newInstance();

        IOStreamInterface iostream = iostream_creator.getStream();
        iostream.openStream();

        InputStream input = iostream.getInputStream();
        OutputStream output = iostream.getOutputStream();

        ByteBuffer byte_buffer = ByteBuffer.allocate(4);
        byte_buffer.order(ByteOrder.LITTLE_ENDIAN);

        byte_buffer.put(version);
        byte_buffer.putShort(encrypting_layout.getEncryptingNumber());
        byte_buffer.put(command_type.registration.value);

        output.write(byte_buffer.array());

        encrypting_layout.setIOStream(iostream);

        encrypting_layout.openStream();

        switch(input.read()) {
            case 0:
                auth = AuthInterface.getAuthTypeByNumber((byte)input.read());
                auth.start(encrypting_layout);
                is_registered = true;

                encrypting_layout.closeStream();
                iostream.closeStream();
                break;
            case 1:
                byte_buffer = ByteBuffer.wrap(input.readNBytes(8));
                byte_buffer.order(ByteOrder.LITTLE_ENDIAN);

                encrypting_layout.closeStream();
                iostream.closeStream();
                throw new RequestError(new String(input.readNBytes((int)byte_buffer.getLong())));
            case 2:
                byte_buffer = ByteBuffer.wrap(input.readNBytes(8));
                byte_buffer.order(ByteOrder.LITTLE_ENDIAN);

                encrypting_layout.closeStream();
                iostream.closeStream();
                throw new ServerError(new String(input.readNBytes((int)byte_buffer.getLong())));
            default:
                encrypting_layout.closeStream();
                iostream.closeStream();
                throw new UnknownStatusError();
        }
    }

    public <T extends EncryptingLayoutInterface> void sendMessage(long id, String message, Class<T> encrypting_class) throws Exception {
        if(!is_registered) throw new ClientHasNotRegisteredError();

        T encrypting_layout = encrypting_class.getConstructor().newInstance();

        IOStreamInterface iostream = iostream_creator.getStream();
        iostream.openStream();

        InputStream input = iostream.getInputStream();
        OutputStream output = iostream.getOutputStream();

        ByteBuffer byte_buffer = ByteBuffer.allocate(4);
        byte_buffer.order(ByteOrder.LITTLE_ENDIAN);

        byte_buffer.put(version);
        byte_buffer.putShort(encrypting_layout.getEncryptingNumber());
        byte_buffer.put(command_type.send_message.value);

        output.write(byte_buffer.array());

        auth.authenticate(iostream);

        byte_buffer = ByteBuffer.allocate( 8 + 8 + message.length());
        byte_buffer.order(ByteOrder.LITTLE_ENDIAN);

        byte_buffer.putLong(id);
        byte_buffer.putLong(message.length());
        byte_buffer.put(message.getBytes(StandardCharsets.UTF_8));

        output.write(byte_buffer.array());

        switch(input.read()) {
            case 0:
                break;
            case 1:
                byte_buffer = ByteBuffer.wrap(input.readNBytes(8));
                byte_buffer.order(ByteOrder.LITTLE_ENDIAN);

                throw new RequestError(new String(input.readNBytes((int)byte_buffer.getLong())));
            case 2:
                byte_buffer = ByteBuffer.wrap(input.readNBytes(8));
                byte_buffer.order(ByteOrder.LITTLE_ENDIAN);

                throw new ServerError(new String(input.readNBytes((int)byte_buffer.getLong())));
            case 3:
                byte_buffer = ByteBuffer.wrap(input.readNBytes(8));
                byte_buffer.order(ByteOrder.LITTLE_ENDIAN);

                throw new UserDoesNotExistsError();
            default:
                throw new UnknownStatusError();
        }

        iostream.closeStream();
    }
    public <T extends EncryptingLayoutInterface> Message[] getMessages(Class<T> encrypting_class) throws Exception {
        if(!is_registered) throw new ClientHasNotRegisteredError();

        T encrypting_layout = encrypting_class.getConstructor().newInstance();

        IOStreamInterface iostream = iostream_creator.getStream();
        iostream.openStream();

        InputStream input = iostream.getInputStream();
        OutputStream output = iostream.getOutputStream();

        ByteBuffer byte_buffer = ByteBuffer.allocate(4);
        byte_buffer.order(ByteOrder.LITTLE_ENDIAN);

        byte_buffer.put(version);
        byte_buffer.putShort(encrypting_layout.getEncryptingNumber());
        byte_buffer.put(command_type.get_messages.value);

        output.write(byte_buffer.array());

        auth.authenticate(iostream);

        switch(input.read()) {
            case 0:
                byte_buffer = ByteBuffer.wrap(input.readNBytes(8));
                byte_buffer.order(ByteOrder.LITTLE_ENDIAN);

                long count = byte_buffer.getLong();

                Message[] messages = new Message[(int)count];
                for(int i = 0; i < count; ++i) {
                    byte_buffer = ByteBuffer.wrap(input.readNBytes(8));
                    byte_buffer.order(ByteOrder.LITTLE_ENDIAN);
                    long sender_id = byte_buffer.getLong();

                    byte_buffer = ByteBuffer.wrap(input.readNBytes(8));
                    byte_buffer.order(ByteOrder.LITTLE_ENDIAN);
                    long length = byte_buffer.getLong();

                    messages[i] = new Message(sender_id, new String(ByteBuffer.wrap(input.readNBytes((int)length)).array()));
                }

                iostream.closeStream();
                return messages;
            case 1:
                byte_buffer = ByteBuffer.wrap(input.readNBytes(8));
                byte_buffer.order(ByteOrder.LITTLE_ENDIAN);

                iostream.closeStream();
                throw new RequestError(new String(input.readNBytes((int)byte_buffer.getLong())));
            case 2:
                byte_buffer = ByteBuffer.wrap(input.readNBytes(8));
                byte_buffer.order(ByteOrder.LITTLE_ENDIAN);

                iostream.closeStream();
                throw new ServerError(new String(input.readNBytes((int)byte_buffer.getLong())));
            default:
                iostream.closeStream();
                throw new UnknownStatusError();
        }
    }
}
