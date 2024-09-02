package ex01;

public class Program {
    public static void main(String[] args) {
        User user1;
        user1 = new User("Loopa", 1000);
        User user2;
        user2 = new User("Poopa", 3000);

        user1.printUserInfo();
        user2.printUserInfo();

        User user3 = new User("Accountant", -5000);
        user3.printUserInfo();
    }
}
