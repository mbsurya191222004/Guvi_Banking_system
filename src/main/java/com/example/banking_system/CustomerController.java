package com.example.banking_system;

import com.example.banking_system.db.MainDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.ResultSet;

import static com.example.banking_system.Main.ShowHome;

public class CustomerController {

    @FXML
    private TableView<Customer> customerTable; // Link TableView

    @FXML
    private TableColumn<Customer, Integer> CustomerID; // Link first column

    @FXML
    private TableColumn<Customer, String> nameColumn;

    @FXML
    private TableColumn<Customer, Long> phoneColumn;

    @FXML
    private TableColumn<Customer, String> panColumn;

    private Connection conn;

    private ObservableList<Customer> customerList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Set up columns
        CustomerID.setCellValueFactory(new PropertyValueFactory<>("custmId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        panColumn.setCellValueFactory(new PropertyValueFactory<>("pan"));

        try {
            conn = MainDB.getConnection(); // get JDBC connection
            loadCustomers(); // load data
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadCustomers() {
        try {
            ResultSet rs = MainDB.ExecuteQuery(conn, "SELECT * FROM customers");

            while (rs != null && rs.next()) {
                int id = rs.getInt("custm_id");
                String name = rs.getString("name");
                long phone = rs.getLong("phone_no");
                String pan = rs.getString("pan");

                customerList.add(new Customer(id, name, phone, pan));
            }

            customerTable.setItems(customerList);

            if (rs != null) rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void goHome(ActionEvent actionEvent) {
        ShowHome();
    }

    // Model class
    public static class Customer {
        private final Integer custmId;
        private final String name;
        private final Long phone;
        private final String pan;

        public Customer(int custmId, String name, long phone, String pan) {
            this.custmId = custmId;
            this.name = name;
            this.phone = phone;
            this.pan = pan;
        }

        public Integer getCustmId() { return custmId; }
        public String getName() { return name; }
        public Long getPhone() { return phone; }
        public String getPan() { return pan; }
    }
}
