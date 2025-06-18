package models;

import java.time.LocalDate;
import java.util.Objects;

public class Expense {
    private String category;
    private double amount;
    private LocalDate date;

    public Expense(String category, double amount, LocalDate date) {
        if (amount < 0) throw new IllegalArgumentException("Amount cannot be negative.");
        this.category = category;
        this.amount = amount;
        this.date = date;
    }

    public Expense(String category, double amount) {
        this(category, amount, LocalDate.now());
    }

    public Expense(double amount, LocalDate date) {
        this("Miscellaneous", amount, date);
    }

    public Expense(double amount) {
        this("Miscellaneous", amount, LocalDate.now());
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        if (amount < 0) throw new IllegalArgumentException("Amount cannot be negative.");
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return String.format("%s | ₹%.2f | %s", category, amount, date);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
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