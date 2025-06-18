import java.time.LocalDate;

public class Expense {
    private String category;
    private double amount;
    private LocalDate date;

    // Public Init Method 
    public Expense(String category, double amount, LocalDate date){
        this.category = category;
        this.amount = amount;
        this.date = date;
    }

    // Methods to invoke items
    public String getcategory() { return category;}
    public double getamount() { return amount;}
    public LocalDate getdate() { return date;}

    // Override toString() to print the values 
    @Override
    public String toString(){
        return date + " | " + category + " | ₹" + amount;
    }
}