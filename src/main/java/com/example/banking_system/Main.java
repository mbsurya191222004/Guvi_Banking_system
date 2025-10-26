package com.example.banking_system;

import com.example.banking_system.db.MainDB;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main extends Application {

    public static void ShowCustomers(Stage stage){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Customers.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Customers");
            stage.setScene(scene);
        } catch (Exception e) {
            try{
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Error.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                stage.setTitle("Hello!");
                stage.setScene(scene);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    public static void ShowTransactions(Stage stage){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Transactions.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Transaction");
            stage.setScene(scene);
        } catch (Exception e) {
            try{
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Error.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                stage.setTitle("Hello!");
                stage.setScene(scene);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }


    @Override
    public void start(Stage stage) throws IOException {
        ShowTransactions(stage);
        stage.show();
    }
}
