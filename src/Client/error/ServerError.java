package Client.error;

public class ServerError extends Exception {
    private final String message;

    public ServerError(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
