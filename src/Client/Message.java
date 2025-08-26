package Client;

public class Message {
    public long sender_id;
    public String message;

    public Message(long sender_id, String message) {
        this.sender_id = sender_id;
        this.message = message;
    }
}
