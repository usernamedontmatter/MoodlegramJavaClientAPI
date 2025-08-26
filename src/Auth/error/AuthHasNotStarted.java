package Auth.error;

public class AuthHasNotStarted extends Exception{
    @Override
    public String getMessage() {
        return "Auth hasn't started";
    }
}