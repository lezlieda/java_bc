package ex04;

import java.util.UUID;

public class Program {
    public static void main(String[] args) {

        User user1 = new User("Loopa", 100);
        User user2 = new User("Poopa", 300);
        User user3 = new User("Accountant", 9000);

        TransactionsService transactionsService = new TransactionsService();

        transactionsService.addUser(user1);
        transactionsService.addUser(user2);
        transactionsService.addUser(user3);

        transactionsService.addTransaction(3, 1, 1000);
        transactionsService.addTransaction(3, 2, 1);
        try {
            transactionsService.addTransaction(2, 1, 1000);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("User1 transactions: ");
        user1.printUserTransactions();
        System.out.println("User2 transactions: ");
        user2.printUserTransactions();
        System.out.println("User3 transactions: ");
        user3.printUserTransactions();

        transactionsService.removeTransaction(1, user1.getTransactionsList().toArray()[0].getId());
        System.out.println("User1 transactions after removing: ");

        try {
            transactionsService.removeTransaction(1, UUID.randomUUID());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("User1 transactions: ");
        Transaction[] user1Transactions = transactionsService.getTransactionsOfUser(1);
        for (int i = 0; i < user1Transactions.length; i++) {
            user1Transactions[i].printTransactionInfo();
        }
        System.out.println("User2 transactions: ");
        Transaction[] user2Transactions = transactionsService.getTransactionsOfUser(2);
        for (int i = 0; i < user2Transactions.length; i++) {
            user2Transactions[i].printTransactionInfo();
        }
        System.out.println("User3 transactions: ");
        Transaction[] user3Transactions = transactionsService.getTransactionsOfUser(3);
        for (int i = 0; i < user3Transactions.length; i++) {
            user3Transactions[i].printTransactionInfo();
        }

        System.out.println("Unpaired transactions: ");
        Transaction[] unpaired = transactionsService.getUnpairedTransactions();
        for (int i = 0; i < unpaired.length; i++) {
            unpaired[i].printTransactionInfo();
        }

        System.out.println("user 1 balance: " + transactionsService.getUserBalance(1));
        System.out.println("user 2 balance: " + transactionsService.getUserBalance(2));
        System.out.println("user 3 balance: " + transactionsService.getUserBalance(3));

    }
}
