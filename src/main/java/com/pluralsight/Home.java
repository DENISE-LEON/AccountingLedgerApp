package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class Home {
    public static Scanner scanner = new Scanner(System.in);
    //create arraylist outside main to access in all methods
    public static ArrayList<Transaction> transactions = new ArrayList<>();


    public static void main(String[] args) {

        //creating the transactions arrayList
        //if save/trans app change greeting

        System.out.println("Welcome to the Accounting App");

        //instead of running the method inside of it's self, created a loop
        //calling method inside itself could lead to crash
        boolean run = true;
        do {
            System.out.println(""" 
                What would you like to do?
                Your options are:
                D) Record a deposit
                W) Record a withdrawal
                L) View your accounting ledger
                X) Exit
                """);
            String homeChoice = scanner.nextLine().trim().toUpperCase();

            //switch case which directs user to desired place
            switch (homeChoice) {
                case "D":
                case "DEPOSIT":
                    moneyInProcess();
                    break;
                case "W":
                case "WITHDRAWAL":
                    moneyOutProcess();
                    break;
                case "L":
                case "VIEW ACCOUNTING LEDGER":
                case "VIEW LEDGER":
                    Ledger.ledgerGuide(scanner, transactions);
                    break;
                case "X":
                case "EXIT":
                    System.out.println("Exiting...");
                    run = false;
                    break;
                default:
                    System.out.println("Invalid input. Please try again (valid input example: D)");
                    //add default for error handle
            }
        } while (run);
    }

    //maybe combine money in and out
    public static void moneyInProcess() {
        int numOfDeposits = numOfTransactionsPrompt();

        //loops through the process numOfDeposits times
        for (int i = 1; i <= numOfDeposits; i++) {
            String type = "Deposit";
            LocalDate date = LocalDate.now();
            LocalTime time = LocalTime.now();
            System.out.print("Enter a short description for deposit #" + i + ": ");
            String description = scanner.nextLine().trim();
            System.out.print("Enter the name of the vendor for deposit #" + i + ": ");
            String vendor = scanner.nextLine().trim();

            //add a try & catch in case user inputs wrong type
            double amount = 0.0;
            boolean validAmtInput = false;
            do {
                try {
                    System.out.print("Enter the amount deposited for deposit #" + i + ": ");
                    amount = Math.abs(scanner.nextDouble());
                    scanner.nextLine();
                    validAmtInput = true;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input, please type a number!");
                    validAmtInput = false;
                    scanner.nextLine();
                }
            } while (!validAmtInput);
            transactionRecorder(type, date, time, description, vendor, amount);
        }
    }

    public static void moneyOutProcess() {

        int numOfWithdrawals = numOfTransactionsPrompt();

        for (int i = 1; i <= numOfWithdrawals; i++) {
            String type = "Withdrawal";
            LocalDate date = LocalDate.now();
            LocalTime time = LocalTime.now();
            System.out.print("Enter a short description for withdrawal #" + i + ": ");
            String description = scanner.nextLine();
            System.out.print("Enter the name of the vendor for withdrawal #" + i + ": ");
            String vendor = scanner.nextLine();

            //add a try & catch in case user inputs wrong type
            double amount = 0.0;
            boolean validAmtInput = false;
            do {
                try {
                    System.out.print("Enter the amount withdrawn for withdrawal #" + i + ": ");
                    amount = -1 * Math.abs(scanner.nextDouble());
                    scanner.nextLine();
                    validAmtInput = true;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input, please type a number!");
                    validAmtInput = false;
                    scanner.nextLine();
                }
            } while (!validAmtInput);
            transactionRecorder(type, date, time, description, vendor, amount);
        }
    }

    public static int numOfTransactionsPrompt() {
        int numofTransactions = 0;
        boolean validInput = false;
        //put into method
        do {
            try {
                System.out.println("How many transactions would you like to record?");
                numofTransactions = Math.abs(scanner.nextInt());
                scanner.nextLine();
                validInput = true;
                //must eat line in catch bc line eater in the try is skipped. Bad input is still in buffer
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, please type a number!");
                validInput = false;
                scanner.nextLine();
            }
        } while (!validInput);
        return numofTransactions;
    }

    public static void transactionRecorder(String type, LocalDate date, LocalTime time, String description, String vendor, double amount) {
        System.out.println();
        System.out.println("Transaction has been successfully recorded.");
        System.out.println();
        //the new transaction is added to the array list
        Transaction newTransaction = new Transaction(type, date, time, description, vendor, amount);
        transactions.add(newTransaction);

        //writer
        String filePath = "transactions.csv";
        try (PrintWriter pwriter = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)))) {
            pwriter.println(newTransaction.getType() + "|" + newTransaction.getDate() + "|" + newTransaction.getTime() + "|" + newTransaction.getDescription() + "|" + newTransaction.getVendor() + "|" + newTransaction.getAmount());
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    public static String[] splitter(String line) {
        return line.split("\\|");
    }

    public static void reader() {

        transactions.clear();
        String filePath = "transactions.csv";
        String line;
        try (BufferedReader bReader = new BufferedReader(new FileReader(filePath))) {
            while ((line = bReader.readLine()) != null) {
                String[] data = splitter(line);

                String type = data[0];
                LocalDate date = LocalDate.parse(data[1]);
                LocalTime time = LocalTime.parse(data[2]);
                String description = data[3];
                String vendor = data[4];
                double amount = Double.parseDouble(data[5]);
                transactions.add(new Transaction(type, date, time, description, vendor, amount));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error");
        }
        transactions.sort(
                Comparator.comparing(Transaction::getDate)
                        .thenComparing(Transaction::getTime)
                        .reversed());
    }


}
