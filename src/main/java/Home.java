import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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
       // int numOfDeposits;
       // try {
            System.out.println("How many deposits would you like to record?");
            int numOfDeposits = scanner.nextInt();
            scanner.nextLine();
       // } catch ()


        //loops through the process numOfDeposits times
        for (int i = 1; i <= numOfDeposits; i++) {
            LocalDate date = LocalDate.now();
            LocalTime time = LocalTime.now();
            System.out.print("Enter a short description for deposit #" + i + ":" + " ");
            String description = scanner.nextLine();
            System.out.print("Enter the name of the vendor for deposit #" + i + ":" + " ");
            String vendor = scanner.nextLine();
            //add a try & catch in case user inputs wrong type
            System.out.print("Enter the amount deposited for deposit #" + i + ":" + " ");
            double amount = scanner.nextDouble();
            scanner.nextLine();

            //each time a deposit is made a new transaction is created
            Transactions newTransaction = new Transactions(date, time, description, vendor, amount);
            newTransaction.moneyIn(description, vendor, amount);
            System.out.println("Deposit has been successfully recorder");
            //the new transaction is added to the array list
            transaction.add(newTransaction);

        }
    }
}
