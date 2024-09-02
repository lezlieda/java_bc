package ex02;

public class User {
    private int id;
    private String name;
    private float balance;

    public User(String name, float balance) {
        if (balance >= 0) {
            this.id = UserIdsGenerator.getInstance().generateId();
            this.balance = balance;
            this.name = name;
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

    public void setName(String name) {
        this.name = name;
    }

    public void printUserInfo() {
        System.out.println("ID: " + id + ", Name: " + name + ", Balance: " + balance);
    }

}
