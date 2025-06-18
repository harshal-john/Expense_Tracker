import java.time.LocalDate;

/* This is a Expense class used as the structure of the inputs i.e. Expenses in .txt format */
public class Expense {
    private String category;
    private double amount;
    private LocalDate date;

    // Public Init Method
    public Expense(String category, double amount, LocalDate date) {
        this.category = category;
        this.amount = amount;
        this.date = date;
    }

    // Method Overloading -- Setting default parameters

    // Constructor with default category
    public Expense(double amount, LocalDate date) {
        this("Miscellaneous", amount, date);
    }

    // Constructor with default date
    public Expense(String category, double amount) {
        this(category, amount, LocalDate.now());
    }

    // Constructor with default category and date
    public Expense(double amount) {
        this("Miscellaneous", amount, LocalDate.now());
    }

    // Methods to invoke items
    public String getcategory() {
        return category;
    }

    public double getamount() {
        return amount;
    }

    public LocalDate getdate() {
        return date;
    }

    // Override toString() to print the values
    @Override
    public String toString() {
        return date + " | " + category + " | ₹" + amount;
    }
}