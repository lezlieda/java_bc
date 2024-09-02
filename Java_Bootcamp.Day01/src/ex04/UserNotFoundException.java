package ex04;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String errMessage) {
        super(errMessage);
    }
}
