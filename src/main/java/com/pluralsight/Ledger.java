package com.pluralsight;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Ledger {

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


        System.out.println("""
                How would you like to view your report?
                Your options are:
                M) Month to date
                PM) Previous month
                Y) Year to date
                PY) Previous year
                V) Search by vendor
                C) Custom search
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


        }

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
        int prevMonth = LocalDate.now().minusMonths(1).getMonthValue();
        int currentYear = LocalDate.now().getYear();

        transaction.stream()
                .filter(t -> t.getDate().getMonthValue() == prevMonth
                        && t.getDate().getYear() == currentYear)
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
        System.out.println("enter the name of the vendor");
        String vendorName = scanner.nextLine().trim().toLowerCase();

        transaction.stream()
                .filter(t -> t.getVendor().toLowerCase().contains(vendorName))
                .forEach(t -> formatPrinter(t));
    }

    public static void formatPrinter(Transaction t) {
        System.out.println(
                t.getType() + "|" + t.getDate() + "|" + t.getTime() + "|" + t.getDescription() + "|" + t.getVendor() + "|" + t.getAmount()
        );
    }

}



