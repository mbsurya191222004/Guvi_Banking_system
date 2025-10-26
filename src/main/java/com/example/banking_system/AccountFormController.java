package com.example.banking_system;

import com.example.banking_system.db.MainDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Alert;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;

import static com.example.banking_system.Main.ShowHome;

public class AccountFormController {

    @FXML
    private TextField balance;

    @FXML
    private TextField custId;

    @FXML
    private ChoiceBox<String> accType;

    private Connection conn;

    // Initialize method runs when the FXML loads
    @FXML
    public void initialize() {
        try {
            conn = MainDB.getConnection(); // JDBC connection
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Initialize ChoiceBox with account types
        accType.getItems().addAll("SAVINGS", "CURRENT");
        accType.setValue("SAVINGS"); // default
    }

    @FXML
    private void addAccount() {
        String balanceText = balance.getText().trim();
        String custIdText = custId.getText().trim();
        String type = accType.getValue();

        // Simple validation
        if (balanceText.isEmpty() || custIdText.isEmpty() || type.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "All fields are required!");
            return;
        }

        BigDecimal balanceValue;
        int customerId;

        try {
            balanceValue = new BigDecimal(balanceText);
            customerId = Integer.parseInt(custIdText);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Balance must be a number and Customer ID must be an integer.");
            return;
        }

        String sql = String.format("INSERT INTO accounts (balance, custm_id, acc_type) VALUES (%f, %d, '%s')",balanceValue,customerId,type.toLowerCase());

        try (PreparedStatement pst = conn.prepareStatement(sql)) {

            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Account added successfully!");
                clearFields();
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to add account. Check if Customer ID exists.");
        }
    }

    // Utility method to show alerts
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Clear fields after successful insert
    private void clearFields() {
        balance.clear();
        custId.clear();
        accType.setValue("SAVINGS");
    }

    public void goHome(ActionEvent actionEvent) {
        ShowHome();
    }
}
