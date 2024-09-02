package ex00;

import java.util.UUID;

public class Transaction {
    public enum Category {
        OUTCOME, INCOME
    };

    private UUID id;
    private User recepient;
    private User sender;
    private float amount;
    private Category category;

    public Transaction(User sender, User recepient, float amount, Category category) {
        if (sender.getId() != 0 && recepient.getId() != 0 &&
                (category == Category.INCOME && amount >= 0) ||
                (category == Category.OUTCOME && amount <= 0)) {
            this.id = UUID.randomUUID();
            this.recepient = recepient;
            this.sender = sender;
            this.category = category;
            this.amount = amount;
        }
    }

    public UUID getId() {
        return id;
    }

    public User getRecepient() {
        return recepient;
    }

    public User getSender() {
        return sender;
    }

    public float getAmount() {
        return amount;
    }

    public Category getCategory() {
        return category;
    }

    public void printTransactionInfo() {
        System.out.println(sender.getName() + " -> " + recepient.getName() + ", " + getAmount() + ", "
                + getCategory() + ", " + getId());
    }

}
