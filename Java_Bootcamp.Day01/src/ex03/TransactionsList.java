package ex03;

import java.util.UUID;

public interface TransactionsList {
    public void addTransaction(Transaction transaction);

    public void removeTransaction(UUID id);

    public void printAllTransactions();

    public int getSize();

    public Transaction[] toArray();

}
