import java.time.LocalDate;

public class Testing {
    public static void main(String[] args) {
        ExpenseManager manager = new ExpenseManager();

        // Adding sample expenses
        manager.addExpense("Movies", 320.00,LocalDate.now());
        manager.addExpense("Food", 250.00, LocalDate.of(2025, 6, 18));
        manager.addExpense("Movies", 720.00);
        manager.addExpense(100.50, LocalDate.of(2025, 6, 17));
        manager.addExpense("Movies", 320.00,LocalDate.of(2024, 12, 25));

        // Displaying all expenses
        System.out.println("Your Expense Records:");
        manager.viewAllExpenses();
        manager.totalByCategory("Movies");
        manager.filterByMonth(6);
    }
}
