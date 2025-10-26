package com.example.banking_system;

import com.example.banking_system.db.MainDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;

public class AccountController {

    @FXML
    private TableView<Account> accountTable;

    @FXML
    private TableColumn<Account, Integer> accNumColumn;

    @FXML
    private TableColumn<Account, BigDecimal> balanceColumn;

    @FXML
    private TableColumn<Account, Integer> custIdColumn;

    @FXML
    private TableColumn<Account, String> accTypeColumn;

    private Connection conn;
    private ObservableList<Account> accountList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Map table columns to Account properties
        accNumColumn.setCellValueFactory(new PropertyValueFactory<>("accNum"));
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));
        custIdColumn.setCellValueFactory(new PropertyValueFactory<>("custmId"));
        accTypeColumn.setCellValueFactory(new PropertyValueFactory<>("accType"));

        try {
            conn = MainDB.getConnection(); // Your JDBC connection
            loadAccounts(); // Load data from DB
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadAccounts() {
        try {
            ResultSet rs = MainDB.ExecuteQuery(conn, "SELECT * FROM accounts");

            while (rs != null && rs.next()) {
                int accNum = rs.getInt("acc_num");
                BigDecimal balance = rs.getBigDecimal("balance");
                int custId = rs.getInt("custm_id");
                String accType = rs.getString("acc_type");

                accountList.add(new Account(accNum, balance, custId, accType));
            }

            accountTable.setItems(accountList);

            if (rs != null) rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Model class for accounts
    public static class Account {
        private final Integer accNum;
        private final BigDecimal balance;
        private final Integer custmId;
        private final String accType;

        public Account(int accNum, BigDecimal balance, int custmId, String accType) {
            this.accNum = accNum;
            this.balance = balance;
            this.custmId = custmId;
            this.accType = accType;
        }

        public Integer getAccNum() { return accNum; }
        public BigDecimal getBalance() { return balance; }
        public Integer getCustmId() { return custmId; }
        public String getAccType() { return accType; }
    }
}
