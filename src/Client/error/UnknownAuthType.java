package Client.error;

public class UnknownAuthType extends Exception{
    @Override
    public String getMessage() {
        return "Authentification type is unknown";
    }
}
