import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Home {
    public static Scanner scanner = new Scanner(System.in);
    //create arraylist outside main to access in all methods
    public static ArrayList<Transaction> transaction = new ArrayList<>();

    public static void main(String[] args) {


        //creating the transactions arrayList

        //if save/trans app change greeting
        System.out.println("Welcome to the Accounting Ledger App");

        AppGuide();

        //test
        //System.out.println(transaction);


    }


    //welcome method: asks the user what they would like to do
    public static void AppGuide() {
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
                ledgerGuide();
                break;
            case "X":
            case "EXIT":
                System.out.println("Exiting...");
                System.exit(0);
            default:
                System.out.println("Invalid input. Please try again");
                AppGuide();


            //add default for error handle
        }
    }

    public static void moneyInProcess() {
        int numOfDeposits = 0;
        boolean validInput = false;
        do {
            try {
                System.out.println("How many deposits would you like to record?");
                numOfDeposits = Math.abs(scanner.nextInt());
                scanner.nextLine();
                validInput = true;
                //must eat line in catch bc line eater in the try is skipped. Bad input is still in buffer
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, please type a number!");
                validInput = false;
                scanner.nextLine();
            }
        } while (!validInput);


        //loops through the process numOfDeposits times

        for (int i = 1; i <= numOfDeposits; i++) {

            LocalDate date = LocalDate.now();
            LocalTime time = LocalTime.now();
            System.out.print("Enter a short description for deposit #" + i + ":" + " ");
            String description = scanner.nextLine();
            System.out.print("Enter the name of the vendor for deposit #" + i + ":" + " ");
            String vendor = scanner.nextLine();
            //add a try & catch in case user inputs wrong type

            double amount = 0.0;
            boolean validAmtInput = false;
            do {
                try {
                    System.out.print("Enter the amount deposited for deposit #" + i + ":" + " ");
                    amount = Math.abs(scanner.nextInt());
                    scanner.nextLine();
                    validAmtInput = true;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input, please type a number!");
                    validAmtInput = false;
                    scanner.nextLine();
                }
            } while (!validAmtInput);
            transactionRecorder(date, time, description, vendor, amount);
        }
    }

    public static void moneyOutProcess() {
        int numOfWithdrawals = 0;
        boolean validInput = false;
        do {
            try {
                System.out.println("How many withdrawals would you like to record?");
                numOfWithdrawals = Math.abs(scanner.nextInt());
                scanner.nextLine();
                validInput = true;
                //must eat line in catch bc line eater in the try is skipped. Bad input is still in buffer
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, please type a number!");
                validInput = false;
                scanner.nextLine();
            }
        } while (!validInput);

        for (int i = 1; i <= numOfWithdrawals; i++) {
            LocalDate date = LocalDate.now();
            LocalTime time = LocalTime.now();
            System.out.print("Enter a short description for deposit #" + i + ":" + " ");
            String description = scanner.nextLine();
            System.out.print("Enter the name of the vendor for deposit #" + i + ":" + " ");
            String vendor = scanner.nextLine();
            //add a try & catch in case user inputs wrong type

            double amount = 0.0;
            boolean validAmtInput = false;
            do {
                try {
                    System.out.print("Enter the amount withdrawaled for withdrawal #" + i + ":" + " ");
                    amount = Math.abs(scanner.nextInt());
                    amount = -Math.abs(amount);
                    scanner.nextLine();
                    validAmtInput = true;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input, please type a number!");
                    validAmtInput = false;
                    scanner.nextLine();
                }
            } while (!validAmtInput);
            transactionRecorder(date, time, description, vendor, amount);
        }

    }

    public static void transactionRecorder(LocalDate date, LocalTime time, String description, String vendor, double amount) {
        System.out.println("Transaction has been successfully recorded");
        //the new transaction is added to the array list
        transaction.add(new Transaction(date, time, description, vendor, amount));
        //insert writer
        //move to transactions class?
    }

    public static void writer(ArrayList<Transaction> transaction) {
        String filePath = "transactions.csv";
        String line;

    }

    public static void reader() {
        String filePath = "transactions.csv";
        String line;
        try(BufferedReader bReader = new BufferedReader(new FileReader(filePath))) {

        } catch(FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error");
        }

    }

        public static void ledgerGuide() {
            System.out.println("""
                    What would you like to do?
                    Your options are:
                    A) View all transactions
                    D) View deposits
                    W) View withdrawals
                    R) View reports
                    """);
            String ledgerChoice = scanner.nextLine().trim().toUpperCase();

            //switch case which directs user to desired place
            switch (ledgerChoice) {


            }

        }
        public static void viewTransactions() {
        //call reader
        }
    }
