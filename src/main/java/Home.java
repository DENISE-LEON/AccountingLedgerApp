import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class Home {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //initializing the transaction class(change later)
        Transactions transaction = new Transactions(LocalDate date, LocalTime time, String description, String vendor, double amount);
        //if save/trans app change greeting
        System.out.println("Welcome to the Accounting Ledger App");
    }
    //welcome method: asks the user what they would like to do
    public static void AppGuide(Scanner scanner) {
        System.out.println(""" 
               What would you like to do?
               Your options are:
               D) Record a deposit
               O) Record money out
               L) View your accounting ledger
               X) Exit
               """);
        String homeChoice = scanner.nextLine().trim();

        //switch case which directs user to desired place
        switch (homeChoice) {
            case "D":
            case "Deposit":
                moneyIn();
        }
    }
}
