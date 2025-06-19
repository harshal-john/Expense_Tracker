package models;
// ExpenseManager.java
// Manages a list of Expense objects and provides file I/O and business logic for the Expense Tracker application.
// Demonstrates encapsulation, modularity, and Java OOP best practices.

import java.io.*;
import java.time.LocalDate;
import java.util.*;

/**
 * Manages a list of expenses, provides file I/O, and business logic.
 */
public class ExpenseManager {
    private final List<Expense> expenses = new ArrayList<>();

    /**
     * Loads all expenses from a CSV file (expense.txt) into memory.
     * @param filename Path to the expense.txt file
     */
    public void loadFromFile(String filename) throws IOException {
        expenses.clear();
        File file = new File(filename);
        if (!file.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String cat = parts[0].trim();
                    double amt;
                    try { amt = Double.parseDouble(parts[1].trim()); } catch (Exception e) { continue; }
                    if (amt < 0) continue;
                    String dateStr = parts[2].trim();
                    LocalDate date;
                    try { date = LocalDate.parse(dateStr); } catch (Exception e) { date = LocalDate.now(); }
                    expenses.add(new Expense(cat, amt, date));
                }
            }
        }
    }

    /**
     * Saves all expenses in memory to a CSV file (expense.txt).
     * @param filename Path to the expense.txt file
     */
    public void saveToFile(String filename) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (Expense e : expenses) {
                bw.write(e.toString());
                bw.newLine();
            }
        }
    }

    /**
     * Appends a list of new expenses to the file and in-memory list.
     * @param newExpenses List of new Expense objects
     * @param filename Path to the expense.txt file
     */
    public void appendExpenses(List<Expense> newExpenses, String filename) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true))) {
            for (Expense e : newExpenses) {
                bw.write(e.toString());
                bw.newLine();
                expenses.add(e);
            }
        }
    }

    /**
     * Returns a copy of all expenses in memory.
     */
    public List<Expense> getAllExpenses() {
        return new ArrayList<>(expenses);
    }

    /**
     * Adds a new expense to the in-memory list.
     */
    public void addExpense(Expense e) {
        expenses.add(e);
    }

    /**
     * Edits an expense at the given index.
     */
    public void editExpense(int index, Expense newExpense) {
        if (index < 0 || index >= expenses.size()) throw new IndexOutOfBoundsException("Invalid index");
        expenses.set(index, newExpense);
    }

    /**
     * Deletes an expense at the given index.
     */
    public void deleteExpense(int index) {
        if (index < 0 || index >= expenses.size()) throw new IndexOutOfBoundsException("Invalid index");
        expenses.remove(index);
    }
} 