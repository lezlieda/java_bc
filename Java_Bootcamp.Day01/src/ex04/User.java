package ex04;

public class User {
    private int id;
    private String name;
    private float balance;
    private TransactionsList transactionsList;

    public User(String name, float balance) {
        if (balance >= 0) {
            this.id = UserIdsGenerator.getInstance().generateId();
            this.balance = balance;
            this.name = name;
            this.transactionsList = new TransactionsLinkedList();
        } else {
            throw new WrongBalanceException("Balance can't be negative");
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void printUserInfo() {
        System.out.println("ID: " + id + ", Name: " + name + ", Balance: " + balance);
    }

    public TransactionsList getTransactionsList() {
        return transactionsList;
    }

    public void printUserTransactions() {
        transactionsList.printAllTransactions();
    }

}
