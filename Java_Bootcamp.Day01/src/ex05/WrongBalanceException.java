package ex05;

public class WrongBalanceException extends RuntimeException {
    public WrongBalanceException(String errMessage) {
        super(errMessage);
    }
}
