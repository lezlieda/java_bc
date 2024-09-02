package ex00;

public class Program {
    public static void main(String[] args) {
        User user1;
        user1 = new User(1, "Loopa", 1000);
        User user2;
        user2 = new User(2, "Poopa", 3000);

        User user3 = new User(3, "Cheetah", -9000);
        User user4 = new User(4, "Cheetah", -9000);
        user4 = new User(4, "Accountant", 9000);

        user1.printUserInfo();
        user2.printUserInfo();
        user3.printUserInfo();
        user4.printUserInfo();

        Transaction transaction1;
        transaction1 = new Transaction(user1, user2, -300, Transaction.Category.OUTCOME);
        Transaction transaction2;
        transaction2 = new Transaction(user2, user1, 300, Transaction.Category.INCOME);
        Transaction transaction3 = new Transaction(user1, user2, 300, Transaction.Category.OUTCOME);
        Transaction transaction4 = new Transaction(user4, user1, -300, Transaction.Category.INCOME);

        transaction1.printTransactionInfo();
        transaction2.printTransactionInfo();
        System.out.println("Transaction3 id = " + transaction3.getId());
        System.out.println("Transaction4 id = " + transaction4.getId());

    }
}
