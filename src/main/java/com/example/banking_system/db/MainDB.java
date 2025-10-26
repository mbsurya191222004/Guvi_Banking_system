package com.example.banking_system.db;

import java.sql.*;

public class MainDB {
    public static ResultSet ExecuteQuery(Connection conn,String query) {
        try{
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(query);

            return rs;
        }catch (Exception e){
            System.out.println("ERROR");
            return null;
        }
    }
    public static int ExecuteUpdate(Connection conn, String query) {
        try {
            Statement stm = conn.createStatement();
            int rowsAffected = stm.executeUpdate(query);

            return rowsAffected; // ✅ number of rows changed (inserted/updated/deleted)

        } catch (Exception e) {
            System.out.println("❌ ERROR: " + e.getMessage());
            return 0; // ❌ or -1 to indicate failure
        }
    }

    //fetch functions
    public static void showTables(Connection conn){
        ResultSet rs =ExecuteQuery(conn,"show tables;");
        try{
            System.out.println("\n📋 Tables in the database:");
            System.out.println("---------------------------");
            while (rs.next()) {
                String tableName = rs.getString(1);
                System.out.println("🧱 " + tableName);
            }

            System.out.println("---------------------------");
        }
        catch (NullPointerException e) {
            System.out.println("NO tables to Show");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static ResultSet ShowAllCustomers(Connection conn) {
        String query = "SELECT * FROM customers";

        try {
            ResultSet rs = ExecuteQuery(conn, query);

            if (rs == null) {
                System.out.println("No data found or error executing query.");
                return null;
            }

            System.out.println("custm_id | name           | phone_no      | pan");
            System.out.println("--------------------------------------------------");

            while (rs.next()) {
                int id = rs.getInt("custm_id");
                String name = rs.getString("name");
                long phone = rs.getLong("phone_no");
                String pan = rs.getString("pan");

                System.out.printf("%-8d | %-14s | %-12d | %s%n", id, name, phone, pan);
            }

            return rs;

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            return null;
        }
    }
    public static void ShowAllAccounts(Connection conn) {
        String query = "SELECT * FROM accounts";

        try {
            ResultSet rs = ExecuteQuery(conn, query);

            if (rs == null) {
                System.out.println("No data found or error executing query.");
                return;
            }

            System.out.println("acc_num      | balance      | custm_id | acc_type");
            System.out.println("------------------------------------------------------");

            while (rs.next()) {
                long accNum = rs.getLong("acc_num");
                double balance = rs.getDouble("balance");
                int custmId = rs.getInt("custm_id");
                String accType = rs.getString("acc_type");

                System.out.printf("%-12d | %-11.2f | %-8d | %s%n", accNum, balance, custmId, accType);
            }

            rs.close(); // close ResultSet after use

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
    public static void ShowAllTransactions(Connection conn) {
        String query = "SELECT * FROM transactions";

        try {
            ResultSet rs = ExecuteQuery(conn, query);

            if (rs == null) {
                System.out.println("No data found or error executing query.");
                return;
            }

            System.out.println("trans_id | sender_acc_num | receiver_acc_num | amount");
            System.out.println("----------------------------------------------------------");

            while (rs.next()) {
                int transId = rs.getInt("trans_id");
                long senderAcc = rs.getLong("sender_acc_num");
                long receiverAcc = rs.getLong("receiver_acc_num");
                double amount = rs.getDouble("amount");

                System.out.printf("%-8d | %-14d | %-16d | %.2f%n", transId, senderAcc, receiverAcc, amount);
            }

            rs.close(); // close ResultSet after use

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    //Add functions
    public static void AddCustomer(Connection conn,String name,long phone,String pan) throws Exception {
        if(String.valueOf(phone).length()!=10){
            throw new Exception("Phone length !=10");

        }
        String query = String.format(
                "INSERT INTO customers (name, phone_no, pan) VALUES ('%s', %d, '%s')",
                name, phone, pan
        );
        int rs = ExecuteUpdate(conn,query);
        if(rs>0){
            System.out.printf("%d rows updated\n",rs);
        }
    }
    public static void AddAccount(Connection conn, long accNum, double balance, int custmId, String accType) throws Exception {
        // Validate account type
        if (!accType.equalsIgnoreCase("savings") && !accType.equalsIgnoreCase("current")) {
            throw new Exception("Account type must be 'savings' or 'current'");
        }

        // Check if customer exists
        String checkQuery = String.format("SELECT * FROM customers WHERE custm_id = %d", custmId);
        ResultSet rsCheck = ExecuteQuery(conn, checkQuery);

        if (rsCheck == null || !rsCheck.next()) { // next() returns false if no row
            throw new Exception("Customer with id " + custmId + " does not exist");
        }

        rsCheck.close(); // close ResultSet after use

        // Build the SQL query to insert account
        String insertQuery = String.format(
                "INSERT INTO accounts (acc_num, balance, custm_id, acc_type) VALUES (%d, %.2f, %d, '%s')",
                accNum, balance, custmId, accType.toLowerCase()
        );

        // Execute update
        int rowsInserted = ExecuteUpdate(conn, insertQuery);

        if (rowsInserted > 0) {
            System.out.printf("%d row(s) inserted into accounts\n", rowsInserted);
        }
    }
    public static void AddTransaction(Connection conn, long senderAccNum, long receiverAccNum, double amount) throws Exception {
        // Validate that sender and receiver are different
        if (senderAccNum == receiverAccNum) {
            throw new Exception("Sender and receiver accounts cannot be the same");
        }

        // Check if sender account exists
        String checkSenderQuery = String.format("SELECT balance FROM accounts WHERE acc_num = %d", senderAccNum);
        ResultSet rsSender = ExecuteQuery(conn, checkSenderQuery);
        if (rsSender == null || !rsSender.next()) {
            throw new Exception("Sender account " + senderAccNum + " does not exist");
        }
        double senderBalance = rsSender.getDouble("balance");
        rsSender.close();

        // Check if receiver account exists
        String checkReceiverQuery = String.format("SELECT * FROM accounts WHERE acc_num = %d", receiverAccNum);
        ResultSet rsReceiver = ExecuteQuery(conn, checkReceiverQuery);
        if (rsReceiver == null || !rsReceiver.next()) {
            throw new Exception("Receiver account " + receiverAccNum + " does not exist");
        }
        rsReceiver.close();

        // Optional: Check if sender has enough balance
        if (senderBalance < amount) {
            throw new Exception("Sender does not have enough balance");
        }

        // Insert the transaction
        String insertQuery = String.format(
                "INSERT INTO transactions (sender_acc_num, receiver_acc_num, amount) VALUES (%d, %d, %.2f)",
                senderAccNum, receiverAccNum, amount
        );

        int rowsInserted = ExecuteUpdate(conn, insertQuery);

        if (rowsInserted > 0) {
            System.out.printf("%d row(s) inserted into transactions\n", rowsInserted);
        }

        // Update sender and receiver balances
        String updateSender = String.format("UPDATE accounts SET balance = balance - %.2f WHERE acc_num = %d", amount, senderAccNum);
        String updateReceiver = String.format("UPDATE accounts SET balance = balance + %.2f WHERE acc_num = %d", amount, receiverAccNum);

        ExecuteUpdate(conn, updateSender);
        ExecuteUpdate(conn, updateReceiver);
    }

    // 1️⃣ Database URL, username, and password
    private static final String URL = "jdbc:mysql://localhost:3306/banking_system";
    private static final String USER = "root";
    private static final String PASSWORD = "1534";

    // 3️⃣ Get a connection
    public static Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Connected to the database!");
            return conn;
        } catch (SQLException e) {
            System.err.println("❌ Failed to connect to database");
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        getConnection();

    }
    // 4️⃣ Example usage

}
