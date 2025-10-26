module com.example.banking_system {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;


    opens com.example.banking_system to javafx.fxml;
    exports com.example.banking_system;
}