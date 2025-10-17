# 💼 Accounting Ledger App

## 🧾 Overview
The **Accounting Ledger App** is a console-based Java application designed to help users **record, track, and review financial transactions**, including deposits and withdrawals.  
It stores transactions in a CSV file, supports reporting by time periods, and provides vendor-specific searches — all within a **user-friendly command-line interface**.

The app uses three main classes:

- **Home** – Main application entry point, handles user input for recording transactions and launching the ledger.
- **Transaction** – Represents a single transaction, including type, date, time, description, vendor, and amount.
- **Ledger** – Handles reading/writing to the CSV, displaying transactions, reports, and formatting outputs.

---

## ✨ Features

### 🧾 Transaction Management

#### Record Deposits and Withdrawals
- Users can record multiple transactions at once.
- Input validation ensures numeric amounts and prevents invalid entries.

#### Transaction Storage
- Transactions are saved to a CSV file (`transactions.csv`) immediately after creation.
- File storage is **persistent** and **appendable**.

---

### 📊 Viewing and Reporting

#### Ledger View
- View all transactions, or filter by deposits or withdrawals.

#### Reports
- Month-to-date
- Previous month
- Year-to-date
- Previous year

#### Vendor Search
- Search transactions by vendor (case-insensitive, partial matches supported).

---

### 🧮 Formatted Display
Transactions display in **aligned columns** with:
- Type
- Date
- Time
- Description
- Vendor
- Amount

---

## 📂 CSV File Structure
Transactions are stored in the CSV file with the following **pipe (`|`) separated format**:

Type|Date|Time|Description|Vendor|Amount

shell
Copy code

### Example:
Withdrawal|2025-10-16|11:06|Electric cat ball|Amazon|-25.99
Deposit|2025-10-15|09:30|Freelance Payment|Upwork|250.00

markdown
Copy code

**Field Descriptions:**
- **Type** – Deposit or Withdrawal
- **Date** – YYYY-MM-DD
- **Time** – HH:mm (24-hour format)
- **Description** – Short description of the transaction
- **Vendor** – Name of the vendor or source
- **Amount** – Positive for deposits, negative for withdrawals

---

## 🧩 Classes Overview

### 🏠 Home
Handles the main application loop and user navigation.

**Responsibilities:**
- Prompts user for transaction input.
- Records deposits and withdrawals.

**Key Methods:**
- `moneyInProcess()` – Records deposits
- `moneyOutProcess()` – Records withdrawals
- `transactionRecorder()` – Adds new transactions to the list and writes them to CSV
- `numOfTransactionsPrompt()` – Safely prompts for a number of transactions
- `reader()` – Reads all transactions from CSV and sorts them by date and time

---

### 💰 Transaction
Represents individual transactions.

**Attributes:**
- `type`
- `date`
- `time`
- `description`
- `vendor`
- `amount`

**Constructors:**

**Includes:**
- Full getters and setters

---

### 📒 Ledger
Manages display and reporting.

**Responsibilities:**
- Reads and filters transactions from the list.
- Formats and displays results.

**Key Methods:**
- `ledgerGuide()` – Main ledger navigation
- `viewAllTransactions()`, `viewDeposits()`, `viewWithdrawals()`
- Reports:
    - `monthToDateDisplay()`
    - `previousMonthDisplay()`
    - `yearToDateDisplay()`
    - `previousYearDisplay()`
    - `searchByVendor()`
- `formatPrinter()` – Nicely formats transaction output in aligned columns

---

## ▶️ How to Run

1. Compile all classes (`Home.java`, `Transaction.java`, `Ledger.java`) in the `com.pluralsight` package.
2. Run the **Home** class.
3. Follow on-screen prompts to record transactions, view the ledger, or generate reports.
4. Transactions are automatically saved in `transactions.csv`.

---

## 💡 Notes / Tips

- **Scanner Sharing:** The same Scanner object is passed to Ledger methods to avoid multiple `System.in` scanners.
- **Clearing Transactions:** Before reading CSV, the transactions list is cleared to avoid duplicates.
- **Formatting:** Amounts and times are aligned for readability using `printf` and `DateTimeFormatter`.
- **Case-Insensitive Search:** Vendor search ignores case and allows partial matching.

---

## 🧠 Pieces of Code That Stood Out for Me

One section that really caught my eye is the use of **streams** in the `Ledger` class to filter and display transactions:

```java
transactions.stream()
    .filter(t -> t.getVendor().toLowerCase().contains(vendorName))
    .forEach(t -> formatPrinter(t)); ```java
```


### 💬 Why This Stood Out

Using **Java Streams** stood out to me because they make the code look **cleaner and shorter** compared to a traditional `for-each` loop.

Normally, to filter and print specific transactions, you’d have to write something like:

```java
for (Transaction t : transactions) {
    if (t.getVendor().toLowerCase().contains(vendorName)) {
        formatPrinter(t);
    }
}
