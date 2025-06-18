import java.util.Scanner;

public class ExpenseTracker {
    public static void main(String[] args) {
        // Creating scanner object
        Scanner scanner = new Scanner(System.in);

        // Create object for Expense Manager
        ExpenseManager manager = new ExpenseManager();

        try{
            manager.loadFormFile("expense.txt");
        } catch(Exception e) {
            System.out.println("No previous data found Exception : " + e);
        }
    }
}
