import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class Home {
    public static Scanner scanner = new Scanner(System.in);
    //create arraylist outside main to access in all methods
    public static ArrayList<Transaction> transaction = new ArrayList<>();


    public static void main(String[] args) {


        //creating the transactions arrayList

        //if save/trans app change greeting
        System.out.println("Welcome to the Accounting Ledger App");

        String runAgain;

        do {
            appGuide();
            System.out.println("Would you like to do something else?(Y or N)");
            runAgain = scanner.nextLine();

        } while (runAgain.equalsIgnoreCase("Y"));
    }


    //welcome method: asks the user what they would like to do
    public static void appGuide() {
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
                System.out.println("Invalid input. Please try again (valid input example: D)");
                appGuide();


                //add default for error handle
        }
    }
//maybe combine money in and out
    public static void moneyInProcess() {
        int numOfDeposits = numOfTransactions();



        //loops through the process numOfDeposits times

        for (int i = 1; i <= numOfDeposits; i++) {
            String type = "Deposit";
            LocalDate date = LocalDate.now();
            LocalTime time = LocalTime.now();
            System.out.print("Enter a short description for deposit #" + i + ":" + " ");
            String description = scanner.nextLine().trim();
            System.out.print("Enter the name of the vendor for deposit #" + i + ":" + " ");
            String vendor = scanner.nextLine().trim();
            //add a try & catch in case user inputs wrong type

            double amount = 0.0;
            boolean validAmtInput = false;
            do {
                try {
                    System.out.print("Enter the amount deposited for deposit #" + i + ":" + " ");
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
        int numOfWithdrawals = numOfTransactions();



        for (int i = 1; i <= numOfWithdrawals; i++) {
            String type = "Withdrawal";
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
                    amount = Math.abs(scanner.nextDouble());
                    amount = -Math.abs(amount);
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

    public static int numOfTransactions() {
        int numOfDeposits = 0;
        boolean validInput = false;
        //put into method
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
        return numOfDeposits;
    }

    public static void transactionRecorder(String type, LocalDate date, LocalTime time, String description, String vendor, double amount) {
        System.out.println("Transaction has been successfully recorded");
        //the new transaction is added to the array list
        Transaction newTransaction = new Transaction(type, date, time, description, vendor, amount);
        transaction.add(newTransaction);
        writer(newTransaction);
        //insert writer
        //move to transactions class?
    }

    public static String[] splitter(String line) {
        String[] data = line.split("\\|");
        return data;
    }

    public static void writer(Transaction newTransaction) {
        String filePath = "transactions.csv";
        try (PrintWriter pwriter = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)))) {
            pwriter.println(newTransaction.getType() + "|" + newTransaction.getDate() + "|" + newTransaction.getTime() + "|" + newTransaction.getDescription() + "|" + newTransaction.getVendor() + "|" + newTransaction.getAmount());


        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error");
        }

    }

    public static void reader() {
        //change tra
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


    public static void ledgerGuide() {
        //calling the reader here bc most of these methods need to read from file
        //avoids duplicate data, avoids having to clear the reader every time
        reader();
        System.out.println("""
                What would you like to do?
                Your options are:
                A) View all transactions
                D) View deposits
                W) View withdrawals
                R) View reports
                H) Go back home
                """);
        String ledgerChoice = scanner.nextLine().trim().toUpperCase();

        //switch case which directs user to desired place
        switch (ledgerChoice) {
            case "A":
            case "VIEW ALL TRANSACTIONS":
                Ledger.viewAllTransactions(transaction);
                break;
            case "D":
            case "VIEW DEPOSITS":
                Ledger.viewDeposits(transaction);
                break;
            case "W":
            case "VIEW WITHDRAWALS":
                Ledger.viewWithdrawals(transaction);
                break;
            case "R":
            case "VIEW REPORTS":
                reportsGuide();
                break;
            case "H":
            case "GO BACK HOME":
                appGuide();
                break;
            default:
                System.out.println("Invalid input. Please try again (valid input example: D)");
                ledgerGuide();


        }

    }


    public static void reportsGuide() {

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
                monthToDateDisplay();
                break;
            case "PM":
            case "PREVIOUS MONTH":
                previousMonthDisplay();
                break;
            case "Y":
            case "YEAR TO DATE":
                yearToDateDisplay();
                break;
            case "PY":
            case "PREVIOUS YEAR":
                previousYearDisplay();
                break;
            case "V":
            case "SEARCH BY VENDOR":
                searchByVendor();
                break;


        }

    }

    public static void monthToDateDisplay() {
        //setting the current day to know so reports are always up to date
        int currentMonth = LocalDate.now().getMonthValue();
        int currentYear = LocalDate.now().getYear();

        transaction.stream()
                .filter(t -> t.getDate().getMonthValue() == currentMonth
                        && t.getDate().getYear() == currentYear)
                .forEach(t -> System.out.println(
                        t.getType() + "|" + t.getDate() + "|" + t.getTime() + "|" + t.getDescription() + "|" + t.getVendor() + "|" + t.getAmount()
                ));
    }

    public static void previousMonthDisplay() {
        int prevMonth = LocalDate.now().minusMonths(1).getMonthValue();
        int currentYear = LocalDate.now().getYear();

        transaction.stream()
                .filter(t -> t.getDate().getMonthValue() == prevMonth
                        && t.getDate().getYear() == currentYear)
                .forEach(t -> System.out.println(
                        t.getType() + "|" + t.getDate() + "|" + t.getTime() + "|" + t.getDescription() + "|" + t.getVendor() + "|" + t.getAmount()
                ));
    }

    public static void yearToDateDisplay() {
        int currentYear = LocalDate.now().getYear();
        transaction.stream()
                .filter(t -> t.getDate().getYear() == currentYear)
                .forEach(t -> System.out.println(
                        t.getType() + "|" + t.getDate() + "|" + t.getTime() + "|" + t.getDescription() + "|" + t.getVendor() + "|" + t.getAmount()
                ));

    }

    public static void previousYearDisplay() {
        int prevYear = LocalDate.now().minusYears(1).getYear();

        transaction.stream()
                .filter(t -> t.getDate().getYear() == prevYear)
                .forEach(t -> System.out.println(
                        t.getType() + "|" + t.getDate() + "|" + t.getTime() + "|" + t.getDescription() + "|" + t.getVendor() + "|" + t.getAmount()
                ));
    }

    public static void searchByVendor() {
        System.out.println("enter the name of the vendor");
        String vendorName = scanner.nextLine();

        transaction.stream()
                .filter(t -> t.getVendor().equalsIgnoreCase(vendorName))
                .forEach(t -> {
                    if ()
                        }
                        System.out.println(
                        t.getType() + "|" + t.getDate() + "|" + t.getTime() + "|" + t.getDescription() + "|" + t.getVendor() + "|" + t.getAmount()
                ));

    }
}

/*.filter(n -> n > 5) // keep numbers greater than 5
        .forEach(n -> {
        // you can still check another condition inside forEach
        if (n % 2 == 0) {
        System.out.println(n + " is even");
                   } else {
                           System.out.println(n + " is odd");
                   }
                           });


 */