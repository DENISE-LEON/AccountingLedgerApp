import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Home {
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        //creating the transactions arrayList
        ArrayList<Transactions> transaction = new ArrayList<>();

        //if save/trans app change greeting
        System.out.println("Welcome to the Accounting Ledger App");

        AppGuide(transaction);
    }


    //welcome method: asks the user what they would like to do
    public static void AppGuide(ArrayList<Transactions> transaction) {
        System.out.println(""" 
                What would you like to do?
                Your options are:
                D) Record a deposit
                O) Record money out
                L) View your accounting ledger
                X) Exit
                """);
        String homeChoice = scanner.nextLine().trim().toUpperCase();

        //switch case which directs user to desired place
        switch (homeChoice) {
            case "D":
            case "DEPOSIT":
                MoneyInRecord(transaction);
                break;

            //add default for error handle
        }
    }

    public static void MoneyInRecord(ArrayList<Transactions> transaction) {
        int numOfDeposits = 0;
        boolean validInput = false;
        do {
            try {
                System.out.println("How many deposits would you like to record?");
                numOfDeposits = scanner.nextInt();
                scanner.nextLine();
                validInput = true;
                if (numOfDeposits < 0) {
                    System.out.println("Please provide a positive number");
                    validInput = false;
                }
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
                    amount = scanner.nextDouble();
                    scanner.nextLine();
                    validAmtInput = true;
                    if(amount < 0) {
                        System.out.println("Please provide a positive number");
                        validAmtInput = false;
                    }
                } catch(InputMismatchException e) {
                    System.out.println("Invalid input, please type a number!");
                    validAmtInput = false;
                    scanner.nextLine();
                }
            } while (!validAmtInput);
            //each time a deposit is made a new transaction is created
            Transactions newTransaction = new Transactions(date, time, description, vendor, amount);
            //redundant?????
            newTransaction.moneyIn(description, vendor, amount);
            System.out.println("Deposit has been successfully recorded");
            //the new transaction is added to the array list
            transaction.add(newTransaction);

        }
    }
}
