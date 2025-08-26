package Client.error;

public class RequestError extends Exception{
    private final String message;

    public RequestError(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}