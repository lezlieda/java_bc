package ex05;

public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException(String errMessage) {
        super(errMessage);
    }

}
