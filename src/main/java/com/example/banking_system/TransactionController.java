package com.example.banking_system;

import com.example.banking_system.db.MainDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.ResultSet;

public class TransactionController {

    @FXML
    private TableView<Transaction> transactionTable;

    @FXML
    private TableColumn<Transaction, Integer> transactionIdColumn;

    @FXML
    private TableColumn<Transaction, String> accountNumberColumn; // We'll show sender -> receiver

    @FXML
    private TableColumn<Transaction, String> transactionDateColumn;

    @FXML
    private TableColumn<Transaction, Double> amountColumn;

    @FXML
    private TableColumn<Transaction, String> transactionTypeColumn;

    private Connection conn;

    private ObservableList<Transaction> transactionList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Set up columns
        transactionIdColumn.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        accountNumberColumn.setCellValueFactory(new PropertyValueFactory<>("accountNumbers"));
        transactionDateColumn.setCellValueFactory(new PropertyValueFactory<>("transactionDate"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        transactionTypeColumn.setCellValueFactory(new PropertyValueFactory<>("transactionType"));

        try {
            conn = MainDB.getConnection();
            loadTransactions();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadTransactions() {
        try {
            ResultSet rs = MainDB.ExecuteQuery(conn, "SELECT * FROM transactions");

            while (rs != null && rs.next()) {
                int id = rs.getInt("trans_id");
                int sender = rs.getInt("sender_acc_num");
                int receiver = rs.getInt("receiver_acc_num");
                double amount = rs.getDouble("amount");
                String date = rs.getString("trans_date");

                // Infer transaction type (example: Debit if sender, Credit if receiver)
                String type = "Transfer";

                transactionList.add(new Transaction(id, sender, receiver, date, amount, type));
            }

            transactionTable.setItems(transactionList);

            if (rs != null) rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Model class
    public static class Transaction {
        private final Integer transactionId;
        private final Integer senderAcc;
        private final Integer receiverAcc;
        private final String transactionDate;
        private final Double amount;
        private final String transactionType;

        public Transaction(int transactionId, int senderAcc, int receiverAcc, String transactionDate, double amount, String transactionType) {
            this.transactionId = transactionId;
            this.senderAcc = senderAcc;
            this.receiverAcc = receiverAcc;
            this.transactionDate = transactionDate;
            this.amount = amount;
            this.transactionType = transactionType;
        }

        public Integer getTransactionId() { return transactionId; }

        // Combine sender and receiver for table display
        public String getAccountNumbers() { return senderAcc + " → " + receiverAcc; }

        public String getTransactionDate() { return transactionDate; }
        public Double getAmount() { return amount; }
        public String getTransactionType() { return transactionType; }
    }
}
