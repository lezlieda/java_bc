package ex04;

import java.util.UUID;

public interface TransactionsList {
    public void addTransaction(Transaction transaction);

    public boolean removeTransaction(UUID id);

    public void printAllTransactions();

    public int getSize();

    public Transaction[] toArray();

}
