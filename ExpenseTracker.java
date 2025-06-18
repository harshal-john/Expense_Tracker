import java.time.LocalDate;
import java.time.Month;
import java.util.Scanner;

public class ExpenseTracker {
    public static void main(String[] args) {
        // Creating scanner object
        Scanner scanner = new Scanner(System.in);

        // Create object for Expense Manager
        ExpenseManager manager = new ExpenseManager();

        try {
            manager.loadFromFile("expense.txt");
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
                    scanner.nextLine();
                    System.out.println("Date (yyyy-mm-dd) : ");
                    String dateStr = scanner.nextLine();

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

                    System.out.println("Expense added successfully!");
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

                // Filter by month
                case 4 -> {
                    System.out.println("Enter the month (1-12) to filter: ");
                    int month = scanner.nextInt();
                    manager.filterByMonth(month);

                    System.out.println("Expenses in " + Month.of(month) + ":");
                    break;
                }

                // Save file and exiting
                case 5 -> {
                    try{
                        manager.saveToFile("expense.txt");
                        System.out.println("Saved... \nExiting...");
                    }catch(Exception e){
                        System.out.println("Failed to save the file. ");
                    }
                    return;
                }

                // Default statement 
                default -> System.out.println("Invalid choice. Please enter a number between 1 and 5.");
            }
        }
    }
}
