package ex03;

public class Program {
    public static void main(String[] args) {
        UsersArrayList usersArrayList = new UsersArrayList();
        User user1 = new User("Loopa", 100);
        User user2 = new User("Poopa", 300);
        User user3 = new User("Accountant", 9000);

        usersArrayList.addUser(user1);
        usersArrayList.addUser(user2);
        usersArrayList.addUser(user3);

        usersArrayList.printUsers();

        Transaction transaction1;
        transaction1 = new Transaction(user3, user2, -300, Transaction.Category.OUTCOME);
        Transaction transaction2;
        transaction2 = new Transaction(user3, user1, -300, Transaction.Category.OUTCOME);

        TransactionsLinkedList user1List = (TransactionsLinkedList) user1.getTransactionsList();
        TransactionsLinkedList user2List = (TransactionsLinkedList) user2.getTransactionsList();
        TransactionsLinkedList user3List = (TransactionsLinkedList) user3.getTransactionsList();
        user3List.addTransaction(transaction1);
        user3List.addTransaction(transaction2);
        user1List.addTransaction(transaction1);
        user2List.addTransaction(transaction2);

        System.out.println("User1 transactions:");
        user1.printUserTransactions();
        System.out.println("User2 transactions:");
        user2.printUserTransactions();
        System.out.println("User3 transactions:");
        user3.printUserTransactions();

        user3List.removeTransaction(transaction1.getId());
        user3List.removeTransaction(transaction2.getId());
        user1List.removeTransaction(transaction1.getId());
        user2List.removeTransaction(transaction2.getId());

        System.out.println("User1 transactions:");
        user1.printUserTransactions();
        System.out.println("User2 transactions:");
        user2.printUserTransactions();
        System.out.println("User3 transactions:");
        user3.printUserTransactions();

    }
}
