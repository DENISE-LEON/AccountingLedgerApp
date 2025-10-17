package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

import com.pluralsight.Home.*;

public class Ledger {

// Splits each line of the CSV into individual fields using the pipe
    public static String[] splitter(String line) {
        //combined into one line that splits returns the split line
        return line.split("\\|");
    }

    public static void reader(ArrayList<Transaction> transaction) {
        //clear the arraylist to avoid duplicates
        transaction.clear();
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
                transaction.add(new Transaction(type, date, time, description, vendor, amount));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error");
        }
        transaction.sort(
                Comparator.comparing(Transaction::getDate)
                        .thenComparing(Transaction::getTime)
                        .reversed());
    }
    // method for viewing and filtering ledger data
    public static void ledgerGuide(Scanner scanner, ArrayList<Transaction> transactions) {
        //calling the reader here bc most of these methods need to read from file
        //avoids duplicate data, avoids having to clear the reader every time
        reader(transactions);

        boolean run = true;
        do {
            System.out.println("﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌");
            System.out.println("""
                    What would you like to do?
                    Your options are:
                    A) View all transactions
                    
                    D) View deposits
                    
                    W) View withdrawals
                    
                    R) View reports
                    
                    H) Go back home
                    """);
            //add pretty formatting
            System.out.println();
            String ledgerChoice = scanner.nextLine().trim().toUpperCase();
            //switch case which directs user to desired place
            switch (ledgerChoice) {
                case "A":
                case "VIEW ALL TRANSACTIONS":
                    Ledger.viewAllTransactions(transactions);
                    break;
                case "D":
                case "VIEW DEPOSITS":
                    Ledger.viewDeposits(transactions);
                    break;
                case "W":
                case "VIEW WITHDRAWALS":
                    Ledger.viewWithdrawals(transactions);
                    break;
                case "R":
                case "VIEW REPORTS":
                    Ledger.reportsGuide(scanner, transactions);
                    break;
                case "H":
                case "GO BACK HOME":
                    run = false;
                    break;
                default:
                    System.out.println("Invalid input. Please try again (valid input example: D)");
            }
        } while (run);
    }

    public static void viewAllTransactions(ArrayList<Transaction> transaction) {

        transaction.stream()
                .forEach(t -> formatPrinter(t));
    }

    public static void viewDeposits(ArrayList<Transaction> transaction) {
        transaction.stream()
                .filter(t -> t.getType().equals("Deposit"))
                .forEach(t -> formatPrinter(t));
    }

    public static void viewWithdrawals(ArrayList<Transaction> transaction) {
        transaction.stream()
                .filter(t -> t.getType().equals("Withdrawal"))
                .forEach(t -> formatPrinter(t));
    }

    //passing the scanner as a parameter, no need to create new one
    public static void reportsGuide(Scanner scanner, ArrayList<Transaction> transaction) {

        transaction.sort(
                Comparator.comparing(Transaction::getDate)
                        .thenComparing(Transaction::getTime));

        boolean run = true;
        do {
            //add in formatting
            System.out.println("﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌﹌");
            System.out.println("""
                    How would you like to view your report?
                    Your options are:
                    M) Month to date
                    
                    PM) Previous month
                    
                    Y) Year to date
                    
                    PY) Previous year
                    
                    V) Search by vendor
                   
                    B) Go back to ledger page
                    """);

            String reportChoice = scanner.nextLine().toUpperCase().trim();
            switch (reportChoice) {
                case "M":
                case "MONTH TO DATE":
                    monthToDateDisplay(transaction);
                    break;
                case "PM":
                case "PREVIOUS MONTH":
                    previousMonthDisplay(transaction);
                    break;
                case "Y":
                case "YEAR TO DATE":
                    yearToDateDisplay(transaction);
                    break;
                case "PY":
                case "PREVIOUS YEAR":
                    previousYearDisplay(transaction);
                    break;
                case "V":
                case "SEARCH BY VENDOR":
                    searchByVendor(scanner, transaction);
                    break;
                case "B":
                case "BACK":
                    run = false;
                default:
                    System.out.println("Invalid input. Please try again (valid input example: D)");
            }
        } while (run);

    }

    public static void monthToDateDisplay(ArrayList<Transaction> transaction) {
        //setting the current day to know so reports are always up to date
        int currentMonth = LocalDate.now().getMonthValue();
        int currentYear = LocalDate.now().getYear();

        transaction.stream()
                .filter(t -> t.getDate().getMonthValue() == currentMonth
                        && t.getDate().getYear() == currentYear)
                .forEach(t -> formatPrinter(t));
    }

    public static void previousMonthDisplay(ArrayList<Transaction> transaction) {

        LocalDate temp = LocalDate.now().minusMonths(1);
        int prevMonth = temp.getMonthValue();
        int prevYear = temp.getYear();

        transaction.stream()
                .filter(t -> t.getDate().getMonthValue() == prevMonth
                        && t.getDate().getYear() == prevYear)
                .forEach(t -> formatPrinter(t));
    }

    public static void yearToDateDisplay(ArrayList<Transaction> transaction) {
        int currentYear = LocalDate.now().getYear();
        transaction.stream()
                .filter(t -> t.getDate().getYear() == currentYear)
                .forEach(t -> formatPrinter(t));
    }

    public static void previousYearDisplay(ArrayList<Transaction> transaction) {
        int prevYear = LocalDate.now().minusYears(1).getYear();
        transaction.stream()
                .filter(t -> t.getDate().getYear() == prevYear)
                .forEach(t -> formatPrinter(t));
    }

    public static void searchByVendor(Scanner scanner, ArrayList<Transaction> transaction) {
        System.out.println("Enter the name of the vendor: ");
        String vendorName = scanner.nextLine().trim().toLowerCase();

        transaction.stream()
                .filter(t -> t.getVendor().toLowerCase().contains(vendorName))
                .forEach(t -> formatPrinter(t));
    }


    public static void formatPrinter(Transaction t) {
        //formatting so everything aligns nicely
        // %n works like new line
        //very glad I turned this into a method
        //data formatter to get rid of milliseconds
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
        System.out.printf(
                "%-12s | %-10s | %-8s | %-25s | %-15s | %-10.2f%n",
                t.getType(),
                t.getDate(),
                t.getTime().format(timeFormat),
                t.getDescription(),
                t.getVendor(),
                t.getAmount()
        );
    }


}







