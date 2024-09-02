package ex05;

import java.util.UUID;

public class TransactionsService {
    UsersArrayList users = new UsersArrayList();

    public void addUser(User user) {
        users.addUser(user);
    }

    public float getUserBalance(int id) {
        return users.getUserById(id).getBalance();
    }

    public void addTransaction(int senderId, int receiverId, float amount) {
        if (senderId == receiverId) {
            throw new IllegalArgumentException("Sender and receiver ids are the same.");
        }
        if (amount <= 0) {
            throw new WrongBalanceException("Amount must be greater than 0.");
        }
        if (getUserBalance(senderId) < amount) {
            throw new IllegalTransactionException("Sender has insufficient funds.");
        }
        UUID id = UUID.randomUUID();
        Transaction transaction = new Transaction(users.getUserById(senderId), users.getUserById(receiverId),
                -amount, Transaction.Category.OUTCOME, id);
        users.getUserById(senderId).getTransactionsList().addTransaction(transaction);
        transaction = new Transaction(users.getUserById(receiverId), users.getUserById(senderId), amount,
                Transaction.Category.INCOME, id);
        users.getUserById(receiverId).getTransactionsList().addTransaction(transaction);
        users.getUserById(senderId).setBalance(users.getUserById(senderId).getBalance() - amount);
        users.getUserById(receiverId).setBalance(users.getUserById(receiverId).getBalance() + amount);
    }

    public void removeTransaction(int userId, UUID id) {
        users.getUserById(userId).getTransactionsList().removeTransaction(id);
    }

    public Transaction[] getTransactionsOfUser(int id) {
        return users.getUserById(id).getTransactionsList().toArray();
    }

    public Transaction[] getUnpairedTransactions() {
        TransactionsLinkedList unpaired = new TransactionsLinkedList();
        for (int i = 0; i < users.getusersNum(); i++) {
            Transaction[] transactions1 = users.getUserByIndex(i).getTransactionsList().toArray();
            for (int j = 0; j < transactions1.length; j++) {
                UUID tmpId = transactions1[j].getId();
                boolean isPaired = false;
                Transaction[] transactions2 = users.getUserById(transactions1[j].getRecepient().getId())
                        .getTransactionsList().toArray();
                for (int k = 0; k < transactions2.length; k++) {
                    if (transactions2[k].getId().equals(tmpId)) {
                        isPaired = true;
                        break;
                    }
                }
                if (!isPaired) {
                    unpaired.addTransaction(transactions1[j]);
                }
            }
        }
        return unpaired.toArray();
    }

}
