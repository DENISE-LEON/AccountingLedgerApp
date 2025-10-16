import java.util.ArrayList;
import java.util.Comparator;

public class Ledger {
  // public static ArrayList<Transaction> transaction =  new ArrayList<>();

    public static void viewAllTransactions(ArrayList<Transaction> transaction) {

        transaction.stream()
                .forEach(t -> System.out.println(
                        t.getType() + "|" + t.getDate() + "|" + t.getTime() + "|" + t.getDescription() + "|" + t.getVendor() + "|" + t.getAmount()
                ));
    }

    public static void viewDeposits(ArrayList<Transaction> transaction) {
        transaction.stream()
                .filter(t -> t.getType().equals("Deposit"))
                .forEach(t -> System.out.println(
                        t.getType() + "|" + t.getDate() + "|" + t.getTime() + "|" + t.getDescription() + "|" + t.getVendor() + "|" + t.getAmount()
                ));
    }

    public static void viewWithdrawals(ArrayList<Transaction> transaction) {
        transaction.stream()
                .filter(t -> t.getType().equals("Withdrawal"))
                .forEach(t -> System.out.println(
                        t.getType() + "|" + t.getDate() + "|" + t.getTime() + "|" + t.getDescription() + "|" + t.getVendor() + "|" + t.getAmount()
                ));
    }

    //is it ok to have a scanner in multiple classes
    //reader??


}
