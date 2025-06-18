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

    // Methods to invoke items
    public String getCatagory() { return catagory;}
    public String getamount() { return catagory;}
    public String getdate() { return catagory;}
}