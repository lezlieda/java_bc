package ex05;

import java.util.UUID;

public class TransactionsLinkedList implements TransactionsList {
    private class Node {
        private Transaction transaction;
        private Node next;

        public Node(Transaction transaction) {
            this.transaction = transaction;
            next = null;
        }
    }

    private Node head;
    private Node tail;
    private int size;

    public TransactionsLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    public void addTransaction(Transaction transaction) {
        Node newNode = new Node(transaction);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;

    }

    public void removeTransaction(UUID id) {
        Node current = head;
        Node previous = null;
        boolean found = false;
        while (current != null) {
            if (current.transaction.getId().equals(id)) {
                if (previous == null) {
                    head = current.next;
                } else {
                    previous.next = current.next;
                }
                size--;
                found = true;
            }
            previous = current;
            current = current.next;
        }
        if (found == false) {
            throw new IllegalArgumentException("Transaction with id " + id + " not found.");
        }
    }

    public void printAllTransactions() {
        Node current = head;
        while (current != null) {
            current.transaction.printTransactionInfo();
            current = current.next;
        }
    }

    public int getSize() {
        return size;
    }

    public Transaction[] toArray() {
        Transaction[] transactions = new Transaction[size];
        Node current = head;
        for (int i = 0; i < size; i++) {
            transactions[i] = current.transaction;
            current = current.next;
        }
        return transactions;
    }
}
