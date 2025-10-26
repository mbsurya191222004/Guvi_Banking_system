package com.example.banking_system;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class Transaction {

    private final SimpleLongProperty transactionId;
    private final SimpleLongProperty accountNumber;
    private final SimpleStringProperty transactionDate;
    private final SimpleDoubleProperty amount;
    private final SimpleStringProperty transactionType;

    public Transaction(long transactionId, long accountNumber, String transactionDate, double amount, String transactionType) {
        this.transactionId = new SimpleLongProperty(transactionId);
        this.accountNumber = new SimpleLongProperty(accountNumber);
        this.transactionDate = new SimpleStringProperty(transactionDate);
        this.amount = new SimpleDoubleProperty(amount);
        this.transactionType = new SimpleStringProperty(transactionType);
    }

    public long getTransactionId() {
        return transactionId.get();
    }

    public long getAccountNumber() {
        return accountNumber.get();
    }

    public String getTransactionDate() {
        return transactionDate.get();
    }

    public double getAmount() {
        return amount.get();
    }

    public String getTransactionType() {
        return transactionType.get();
    }
}

