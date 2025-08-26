package Client.error;

public class UnknownStatusError extends Exception{
    @Override
    public String getMessage() {
        return "Status is unknown";
    }
}