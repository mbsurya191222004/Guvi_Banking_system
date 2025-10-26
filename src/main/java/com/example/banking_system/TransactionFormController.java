package com.example.banking_system;

import com.example.banking_system.db.MainDB;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static com.example.banking_system.Main.ShowHome;

public class TransactionFormController {

    @FXML
    private TextField senderAcc;

    @FXML
    private TextField receiverAcc;

    @FXML
    private TextField amount;

    private Connection conn;

    @FXML
    public void initialize() {
        try {
            conn = MainDB.getConnection(); // Get JDBC connection
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addTransaction() {
        String senderText = senderAcc.getText().trim();
        String receiverText = receiverAcc.getText().trim();
        String amountText = amount.getText().trim();

        if (senderText.isEmpty() || receiverText.isEmpty() || amountText.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "All fields are required!");
            return;
        }

        int senderId, receiverId;
        BigDecimal transAmount;

        try {
            senderId = Integer.parseInt(senderText);
            receiverId = Integer.parseInt(receiverText);
            transAmount = new BigDecimal(amountText);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Account numbers must be integers and amount must be numeric.");
            return;
        }

        try {
            conn.setAutoCommit(false); // Transaction management

            // Check sender balance
            BigDecimal senderBalance = getBalance(senderId);
            if (senderBalance == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "Sender account does not exist.");
                conn.rollback();
                return;
            }

            if (senderBalance.compareTo(transAmount) < 0) {
                showAlert(Alert.AlertType.ERROR, "Error", "Insufficient balance in sender account.");
                conn.rollback();
                return;
            }

            // Check receiver account
            if (!accountExists(receiverId)) {
                showAlert(Alert.AlertType.ERROR, "Error", "Receiver account does not exist.");
                conn.rollback();
                return;
            }

            // Insert transaction
            String insertSql = "INSERT INTO transactions (sender_acc_num, receiver_acc_num, amount) VALUES (?, ?, ?)";
            try (PreparedStatement pst = conn.prepareStatement(insertSql)) {
                pst.setInt(1, senderId);
                pst.setInt(2, receiverId);
                pst.setBigDecimal(3, transAmount);
                pst.executeUpdate();
            }

            // Update balances
            updateBalance(senderId, senderBalance.subtract(transAmount));
            BigDecimal receiverBalance = getBalance(receiverId);
            updateBalance(receiverId, receiverBalance.add(transAmount));

            conn.commit();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Transaction completed successfully!");
            clearFields();

        } catch (Exception e) {
            e.printStackTrace();
            try { conn.rollback(); } catch (Exception ex) { ex.printStackTrace(); }
            showAlert(Alert.AlertType.ERROR, "Error", "Transaction failed. Check account numbers and balances.");
        } finally {
            try { conn.setAutoCommit(true); } catch (Exception e) { e.printStackTrace(); }
        }
    }

    private BigDecimal getBalance(int accNum) throws Exception {
        String sql = "SELECT balance FROM accounts WHERE acc_num = ?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, accNum);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getBigDecimal("balance");
            }
        }
        return null;
    }

    private boolean accountExists(int accNum) throws Exception {
        String sql = "SELECT 1 FROM accounts WHERE acc_num = ?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, accNum);
            ResultSet rs = pst.executeQuery();
            return rs.next();
        }
    }

    private void updateBalance(int accNum, BigDecimal newBalance) throws Exception {
        String sql = "UPDATE accounts SET balance = ? WHERE acc_num = ?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setBigDecimal(1, newBalance);
            pst.setInt(2, accNum);
            pst.executeUpdate();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        senderAcc.clear();
        receiverAcc.clear();
        amount.clear();
    }

    @FXML
    private void goHome() {
        ShowHome();
    }
}
