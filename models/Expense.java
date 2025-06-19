package models;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents a single expense with category, amount, and date.
 */
public class Expense {
    private String category;
    private double amount;
    private LocalDate date;

    /**
     * Full constructor for Expense.
     * @param category Expense category
     * @param amount Expense amount
     * @param date Expense date
     */
    public Expense(String category, double amount, LocalDate date) {
        this.category = category;
        this.amount = amount;
        this.date = date;
    }

    /**
     * Constructor with category and amount, uses today's date.
     */
    public Expense(String category, double amount) {
        this(category, amount, LocalDate.now());
    }

    /**
     * Constructor with amount and date, uses 'Miscellaneous' as category.
     */
    public Expense(double amount, LocalDate date) {
        this("Miscellaneous", amount, date);
    }

    /**
     * Constructor with only amount, uses 'Miscellaneous' and today's date.
     */
    public Expense(double amount) {
        this("Miscellaneous", amount, LocalDate.now());
    }

    // --- Getters and Setters ---
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    /**
     * Returns a CSV string representation: Category,Amount,Date
     */
    @Override
    public String toString() {
        return String.format("%s,%.2f,%s", category, amount, date);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Expense)) return false;
        Expense expense = (Expense) o;
        return Double.compare(expense.amount, amount) == 0 &&
                Objects.equals(category, expense.category) &&
                Objects.equals(date, expense.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, amount, date);
    }
} 