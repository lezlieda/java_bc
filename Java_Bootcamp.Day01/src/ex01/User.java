package ex01;

public class User {
    private int id;
    private String name;
    private float balance;

    public User(String name, float balance) {
        if (balance >= 0) {
            this.id = UserIdsGenerator.getInstance().generateId();
            this.name = name;
            this.balance = balance;
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
        if (balance < 0) {
            this.balance = 0;
        } else {
            this.balance = balance;
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void printUserInfo() {
        System.out.println("ID: " + id + ", Name: " + name + ", Balance: " + balance);
    }

}
