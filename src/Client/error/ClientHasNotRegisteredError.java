package Client.error;

public class ClientHasNotRegisteredError extends RuntimeException {
    @Override
    public String getMessage() {
        return "Client hasn't registered";
    }
}
