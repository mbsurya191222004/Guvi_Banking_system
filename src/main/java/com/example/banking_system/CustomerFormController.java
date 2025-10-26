package com.example.banking_system;
import com.example.banking_system.db.MainDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;

import static com.example.banking_system.Main.ShowHome;

public class CustomerFormController {

    @FXML
    private TextField name;

    @FXML
    private TextField phone;

    @FXML
    private TextField pan;

    private Connection conn;

    // This method runs when the FXML is initialized
    @FXML
    public void initialize() {
        try {
            conn = MainDB.getConnection(); // Your JDBC connection method
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addCustomer() {
        String custName = name.getText().trim();
        String phoneNo = phone.getText().trim();
        String panNo = pan.getText().trim();

        // Simple validation
        if (custName.isEmpty() || phoneNo.isEmpty() || panNo.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "All fields are required!");
            return;
        }

        String sql = "INSERT INTO customers (name, phone_no, pan) VALUES (?, ?, ?)";

        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, custName);
            pst.setString(2, phoneNo);
            pst.setString(3, panNo);

            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Customer added successfully!");
                clearFields();
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to add customer. Check for duplicate phone or PAN.");
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

    // Clear text fields after successful insert
    private void clearFields() {
        name.clear();
        phone.clear();
        pan.clear();
    }

    public void goHome(ActionEvent actionEvent) {
        ShowHome();
    }
}

