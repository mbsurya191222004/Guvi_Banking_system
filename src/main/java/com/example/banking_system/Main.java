package com.example.banking_system;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    static Stage stage;
    public static void ShowCustomers(){
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
    public static void ShowTransactions(){
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
    public static void ShowAccounts(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Accounts.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Accounts");
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
    public static void ShowHome(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Home.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Home");
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
        this.stage = stage;
        ShowHome();
        stage.show();
    }
}
