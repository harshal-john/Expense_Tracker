import java.time.LocalDate;
import java.util.Scanner;

public class ExpenseTracker {
    public static void main(String[] args) {
        // Creating scanner object
        Scanner scanner = new Scanner(System.in);

        // Create object for Expense Manager
        ExpenseManager manager = new ExpenseManager();

        try {
            manager.loadFormFile("expense.txt");
        } catch (Exception e) {
            System.out.println("No previous data found Exception : " + e);
        }

        while (true) {

            // Printing statements
            System.out.println("\n--- Expense Tracker ---");
            System.out.println("1. Add Expense");
            System.out.println("2. View All Expenses");
            System.out.println("3. View Total by Category");
            System.out.println("4. Filter by Month");
            System.out.println("5. Save & Exit");
            System.out.print("Choose: ");

            // Getting input for choice
            int choice = scanner.nextInt();
            scanner.nextLine();

            // switch case for displaying options
            switch (choice) {

                // case 1 -> Add Expenses
                case 1 -> {
                    System.out.println("Category : ");
                    String category = scanner.nextLine();
                    System.out.println("Amount : ");
                    double amount = scanner.nextDouble();
                    System.out.println("Date (yyyy-mm-dd) : ");
                    String dateStr = scanner.next();

                    // Check for deault parameter
                    boolean hasCategory = !category.isBlank();
                    boolean hasDate = !dateStr.isBlank();

                    // Calling methods for each Default parameters 
                    if (hasCategory && hasDate) {
                        manager.addExpense(category, amount, LocalDate.parse(dateStr));
                    } else if (hasCategory) {
                        manager.addExpense(category, amount); // default date
                    } else if (hasDate) {
                        manager.addExpense(amount, LocalDate.parse(dateStr)); // default category
                    } else {
                        manager.addExpense(amount); // default category & date
                    }
                    break;
                }

                // view all Expenses
                case 2 -> {
                    manager.viewAllExpenses();
                    break;
                }
                
                // Total amount by category
                case 3 -> {
                    System.out.println("Category to total : ");
                    String category = scanner.nextLine();
                    manager.totalByCategory(category);
                    break;
                }
                case 4 -> {
                    System.out.println("Enter the month (1-12) to filter: ");
                    int month = scanner.nextInt();
                    manager.filterByMonth(month);
                    break;
                }
                case 5 -> {
                    try{
                        manager.saveToFile("expense.txt");
                        System.out.println("Saved... \nExiting...");
                    }catch(Exception e){
                        System.out.println("Failed to save the file. ");
                    }
                    break;
                }
                default -> System.out.println("Invaild Choice please enter choices (1 - 5)");
            }
        }
    }
}
