package Client.error;

public class UserDoesNotExistsError extends Exception {
    @Override
    public String getMessage() {
        return "User doesn't exists";
    }
}