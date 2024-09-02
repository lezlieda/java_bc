package ex02;

public class Program {
    public static void main(String[] args) {
        UsersArrayList usersArrayList = new UsersArrayList();
        User user1 = new User("Loopa", 100);
        User user2 = new User("Poopa", 300);
        User user3 = new User("Accountant", 9000);
        User user4;
        try {
            user4 = new User("Test", -9000);
        } catch (WrongBalanceException e) {
            System.out.println(e.getMessage());
        }
        user4 = new User("Cheetah", 9000);

        usersArrayList.addUser(user1);
        usersArrayList.addUser(user2);
        usersArrayList.addUser(user3);
        usersArrayList.addUser(user4);

        System.out.println("Users count: " + usersArrayList.getusersNum());
        usersArrayList.getUserByIndex(0).printUserInfo();
        usersArrayList.getUserByIndex(1).printUserInfo();
        usersArrayList.getUserByIndex(2).printUserInfo();
        usersArrayList.getUserByIndex(3).printUserInfo();

        usersArrayList.getUserById(1).printUserInfo();
        usersArrayList.getUserById(2).printUserInfo();
        usersArrayList.getUserById(3).printUserInfo();
        usersArrayList.getUserById(4).printUserInfo();

    }
}
