package com.example.banking_system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import static com.example.banking_system.Main.*;

public class HomeController {
    public void goToCustomers(ActionEvent actionEvent) {
        ShowCustomers();
    }

    public void goToAccounts(ActionEvent actionEvent) {
        ShowAccounts();
    }

    public void goToTransactions(ActionEvent actionEvent) {
        ShowTransactions();
    }
}
