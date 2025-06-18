import java.time.LocalDate;

public class Expense {
    private String catagory;
    private double amount;
    private LocalDate date;

    // Public Init Method 
    public Expense(String catagory, double amount, LocalDate date){
        this.catagory = catagory;
        this.amount = amount;
        this.date = date;
    }
}