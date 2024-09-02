package ex04;

public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException(String errMessage) {
        super(errMessage);
    }

}
