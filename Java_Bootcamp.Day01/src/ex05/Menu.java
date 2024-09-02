package ex05;

import java.util.Scanner;
import java.util.UUID;

public class Menu {
    TransactionsService transactionsService = new TransactionsService();
    Scanner scanner = new Scanner(System.in);

    public void showMenu(boolean dev) {
        System.out.println("1. Add user");
        System.out.println("2. View user balances");
        System.out.println("3. Perform a transfer");
        System.out.println("4. View all transactions for a specific user");
        if (dev == false) {
            System.out.println("5. Exit");
        } else {

            System.out.println("5. DEV – remove a transfer by ID");
            System.out.println("6. DEV – check transfer validity");
            System.out.println("7. Finish execution");
        }
    }

    public void run(boolean dev) {
        System.out.println();
        showMenu(dev);
        int choice = scanner.nextInt();
        while (true) {
            switch (choice) {
                case 1:
                    addUser();
                    break;
                case 2:
                    viewUserBalances();
                    break;
                case 3:
                    performTransfer();
                    break;
                case 4:
                    viewAllTransactionsForUser();
                    break;
                case 5:
                    if (dev == false) {
                        System.out.println("Exiting...");
                        System.exit(0);
                    } else {
                        removeTransferById();
                        break;
                    }
                case 6:
                    if (dev == false) {
                        System.out.println("Invalid choice");
                        break;
                    } else {
                        checkTransferValidity();
                        break;
                    }
                case 7:
                    if (dev == false) {
                        System.out.println("Invalid choice");
                        break;
                    } else {
                        System.out.println("Exiting...");
                        return;
                    }
                default:
                    System.out.println("Invalid choice");
                    break;
            }
            showMenu(dev);
            choice = scanner.nextInt();
        }

    }

    public void addUser() {
        System.out.println("Enter a user name and a balance");
        try {
            String name = scanner.next();
            float balance = scanner.nextFloat();
            User newUser;
            newUser = new User(name, balance);
            transactionsService.addUser(newUser);
        } catch (WrongBalanceException | IllegalTransactionException e) {
            System.out.println(e.getMessage());
        }

    }

    public void viewUserBalances() {
        System.out.println("Enter a user ID");
        try {
            int id = scanner.nextInt();
            System.out.println("User balance: " + transactionsService.getUserBalance(id));
        } catch (IllegalTransactionException | UserNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void performTransfer() {
        System.out.println("Enter a sender ID, a recipient ID, and a transfer amount");
        try {
            int senderId = scanner.nextInt();
            int receiverId = scanner.nextInt();
            float amount = scanner.nextFloat();

            transactionsService.addTransaction(senderId, receiverId, amount);
            System.out.println("The transfer is completed");
        } catch (IllegalTransactionException | WrongBalanceException | IllegalArgumentException
                | UserNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void viewAllTransactionsForUser() {
        System.out.println("Enter a user ID");
        try {
            int id = scanner.nextInt();
            Transaction[] transactions = transactionsService.getTransactionsOfUser(id);
            for (int i = 0; i < transactions.length; i++) {
                transactions[i].printTransactionInfo();
            }
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeTransferById() {
        System.out.println("Enter a user ID and a transfer ID");
        try {
            int userId = scanner.nextInt();
            String id = scanner.next();
            transactionsService.removeTransaction(userId, UUID.fromString(id));
            System.out.println("The transfer is removed");
        } catch (IllegalArgumentException | UserNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void checkTransferValidity() {
        Transaction[] unpaired = transactionsService.getUnpairedTransactions();
        if (unpaired.length == 0) {
            System.out.println("All transfers are valid");
        } else {
            System.out.println("Unpaired transactions: ");
            for (int i = 0; i < unpaired.length; i++) {
                unpaired[i].printTransactionInfo();
            }
        }
    }

}
