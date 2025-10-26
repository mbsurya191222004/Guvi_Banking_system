# Guvi Banking System

A comprehensive banking application developed in Java, designed to simulate real-world banking operations such as account creation, deposits, withdrawals, and transaction history management.

---

## 🧠 Project Overview

The Guvi Banking System is a console-based application that emulates essential banking functionalities. It provides a user-friendly interface for managing bank accounts and performing transactions, making it an ideal project for understanding object-oriented programming concepts and Java development.

---

## ⚙️ Features

* **Account Management**: Create and manage multiple bank accounts.
* **Transactions**: Deposit and withdraw funds with real-time balance updates.
* **Transaction History**: View detailed transaction reports.
* **Interest Calculation**: Apply interest rates based on account types.
* **Account Deletion**: Close accounts and remove associated data.

---

## 🛠️ Technologies Used

* **Programming Language**: Java
* **Development Environment**: Any Java IDE (e.g., IntelliJ IDEA, Eclipse)
* **Build Tool**: Maven

---

## 📁 Project Structure

The project follows a modular structure for maintainability and scalability:

```
Guvi_Banking_system/
├── .mvn/
│   └── wrapper/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── guvi/
│                   └── banking/
│                       ├── Account.java
│                       ├── Transaction.java
│                       └── BankSystem.java
├── .gitignore
├── mvnw
├── mvnw.cmd
└── pom.xml
```

---

## 🚀 Getting Started

### Prerequisites

* Java Development Kit (JDK) 11 or higher
* Apache Maven

### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/mbsurya191222004/Guvi_Banking_system.git
   ```

2. Navigate to the project directory:

   ```bash
   cd Guvi_Banking_system
   ```

3. Build the project using Maven:

   ```bash
   mvn clean install
   ```

4. Run the application:

   ```bash
   mvn exec:java
   ```

---

## 📄 Usage

Upon running the application, users are presented with a menu to choose from various banking operations. The system prompts for necessary inputs and displays appropriate messages based on the actions performed.

---
