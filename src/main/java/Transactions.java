import java.time.LocalDate;
import java.time.LocalTime;

public class Transactions {
    private LocalDate date;
    private LocalTime time;
    private String description;
    private String vendor;
    private double amount;

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    public String getVendor() {
        return vendor;
    }

    public double getAmount() {
        return amount;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Transactions(LocalDate date, LocalTime time, String description, String vendor, double amount) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }
//methods
    public void moneyIn(String description, String vendor, double amount) {
        this.date = LocalDate.now();
        this.time = LocalTime.now();
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
        //add total method maybe? using a for each loop
    }

    //sets the time to whatever the time of the data input is and updates the info
    //must use this bc transactions is an object and must let java know the variables are the same
    public void moneyOut(String description, String vendor, double amount) {
        this.date = LocalDate.now();
        this.time = LocalTime.now();
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    //updates the total, returns the value and is updated each time a transaction is added

    /*public double totalCalc(insertArrayList double amount) {
        double total = 0; //declaring the variable
    }
   */
}
