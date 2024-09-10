package exceptions;

public class UserServiceLogicException extends RuntimeException {
    public UserServiceLogicException(String message) {
        super(message);
    }
}