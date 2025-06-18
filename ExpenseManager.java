import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Class to manage a list of expenses
public class ExpenseManager {

    // List to store all the expenses
    private List<Expense> expenses = new ArrayList<>();

    // Method to add a new expense to the list
    public void addExpense(String category, double amount, LocalDate date) {
        // Create a new Expense object with provided details
        Expense e = new Expense(category, amount, date);

        // Add the expense to the list
        expenses.add(e);
    }
}

