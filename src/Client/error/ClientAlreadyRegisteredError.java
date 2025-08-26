package Client.error;

public class ClientAlreadyRegisteredError extends Exception {
    @Override
    public String getMessage() {
        return "Client already registered";
    }
}
