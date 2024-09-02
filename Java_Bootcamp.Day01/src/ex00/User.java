package ex00;

public class User {
    private int id;
    private String name;
    private float balance;

    public User(int id, String name, float balance) {
        if (balance >= 0) {
            this.id = id;
            this.balance = balance;
            this.name = name;
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
