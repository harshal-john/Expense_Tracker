import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * ExpenseManager manages a list of expenses and provides functionalities
 * like adding, viewing, filtering, totaling, saving to file, and loading from file.
 */

 
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

    // Overloading the method for Default parameters
    public void addExpense(double amount, LocalDate date) {
        expenses.add(new Expense(amount, date));
    }

    public void addExpense(String category, double amount) {
        expenses.add(new Expense(category, amount));
    }

    public void addExpense(double amount) {
        expenses.add(new Expense(amount));
    }

    // Method to view all Expenses
    public void viewAllExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No Expenses found. ");
            return;
        }
        for (Expense e : expenses) {
            System.out.println(e);
        }
    }

    // Method to total the amount by category
    public void totalByCategory(String  category){
        double total = 0;
        for (Expense e : expenses) {
            if (e.getCategory().equalsIgnoreCase(category)) {
                total += e.getAmount();
            }
        }
        System.out.println("Total amount in " + category + " is \u20B9" + total);
    }

    // Method to filter Expenses by month
    public void filterByMonth(int month){
        for (Expense e : expenses) {
            if (e.getDate().getMonthValue() == month) {
                System.out.println(e);
            }
        }
    }

    // Method to save file 
    public void saveToFile(String filename) throws IOException {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (Expense e : expenses) {
                bw.write(e.getCategory() + " ," + e.getAmount() + " ,"+ e.getDate());
                bw.newLine();
            }
        }
    }

    // Method to load data from file 
    public void loadFormFile(String filename) throws IOException {
        expenses.clear();
        File file = new File(filename);
        if(!file.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String cat = parts[0];
                    double amt = Double.parseDouble(parts[1]);
                    LocalDate date = LocalDate.parse(parts[2]);
                    addExpense(cat, amt, date);
                }
            }
        }
    }

}
