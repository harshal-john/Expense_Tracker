package models; 
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.util.List;
import java.io.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

public class ExpenseManager {
    private final List<Expense> expenses = new ArrayList<>();

    // --- Sorting ---
    public void sortByDateAscending() {
        expenses.sort(Comparator.comparing(Expense::getDate));
    }
    public void sortByDateDescending() {
        expenses.sort(Comparator.comparing(Expense::getDate).reversed());
    }
    public void sortByAmountAscending() {
        expenses.sort(Comparator.comparingDouble(Expense::getAmount));
    }
    public void sortByAmountDescending() {
        expenses.sort(Comparator.comparingDouble(Expense::getAmount).reversed());
    }
    public void sortByCategory() {
        expenses.sort(Comparator.comparing(e -> e.getCategory().toLowerCase()));
    }
    public void sortByCategoryThenDate() {
        expenses.sort(Comparator.comparing((Expense e) -> e.getCategory().toLowerCase()).thenComparing(Expense::getDate));
    }

    // --- Filtering ---
    public List<Expense> filterByCategory(String category) {
        return expenses.stream()
                .filter(e -> e.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }
    public List<Expense> filterByMonth(YearMonth month) {
        return expenses.stream()
                .filter(e -> YearMonth.from(e.getDate()).equals(month))
                .collect(Collectors.toList());
    }
    public List<Expense> filterByAmountRange(double min, double max) {
        return expenses.stream()
                .filter(e -> e.getAmount() >= min && e.getAmount() <= max)
                .collect(Collectors.toList());
    }

    // --- Analytics ---
    public double getTotalAmount() {
        return expenses.stream().mapToDouble(Expense::getAmount).sum();
    }
    public double getTotalByCategory(String category) {
        return expenses.stream()
                .filter(e -> e.getCategory().equalsIgnoreCase(category))
                .mapToDouble(Expense::getAmount).sum();
    }
    public double getMonthlySummary(YearMonth month) {
        return filterByMonth(month).stream().mapToDouble(Expense::getAmount).sum();
    }
    public double getAverageDailySpending(YearMonth month) {
        List<Expense> monthExpenses = filterByMonth(month);
        Set<LocalDate> days = monthExpenses.stream().map(Expense::getDate).collect(Collectors.toSet());
        if (days.isEmpty()) return 0.0;
        return monthExpenses.stream().mapToDouble(Expense::getAmount).sum() / days.size();
    }

    // --- Editing ---
    public void editExpense(int index, Expense newExpense) {
        if (index < 0 || index >= expenses.size()) throw new IndexOutOfBoundsException("Invalid index");
        if (newExpense.getAmount() < 0) throw new IllegalArgumentException("Amount cannot be negative");
        expenses.set(index, newExpense);
    }
    public void deleteExpense(int index) {
        if (index < 0 || index >= expenses.size()) throw new IndexOutOfBoundsException("Invalid index");
        expenses.remove(index);
    }
    public void deleteByCategory(String category) {
        expenses.removeIf(e -> e.getCategory().equalsIgnoreCase(category));
    }

    // --- Utility ---
    public List<Expense> getAllExpenses() {
        return new ArrayList<>(expenses);
    }
    public void printAllExpenses() {
        System.out.printf("%-15s %-10s %-12s\n", "Category", "Amount", "Date");
        System.out.println("--------------------------------------");
        for (Expense e : expenses) {
            System.out.printf("%-15s ₹%-9.2f %-12s\n", e.getCategory(), e.getAmount(), e.getDate());
        }
    }

    // --- File I/O ---
    public void saveToFile(String filename) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (Expense e : expenses) {
                bw.write(e.getCategory() + "," + e.getAmount() + "," + e.getDate());
                bw.newLine();
            }
        }
    }
    public void loadFromFile(String filename) throws IOException {
        expenses.clear();
        File file = new File(filename);
        if (!file.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String cat = parts[0];
                    double amt = Double.parseDouble(parts[1]);
                    LocalDate date = LocalDate.parse(parts[2]);
                    expenses.add(new Expense(cat, amt, date));
                }
            }
        }
    }

    // --- Add Expense ---
    public void addExpense(String category, double amount, LocalDate date) {
        if (amount < 0) throw new IllegalArgumentException("Amount cannot be negative.");
        expenses.add(new Expense(category, amount, date));
    }
    public void addExpense(double amount, LocalDate date) {
        if (amount < 0) throw new IllegalArgumentException("Amount cannot be negative.");
        expenses.add(new Expense(amount, date));
    }
    public void addExpense(String category, double amount) {
        if (amount < 0) throw new IllegalArgumentException("Amount cannot be negative.");
        expenses.add(new Expense(category, amount));
    }
    public void addExpense(double amount) {
        if (amount < 0) throw new IllegalArgumentException("Amount cannot be negative.");
        expenses.add(new Expense(amount));
    }

    // --- Excel Export (already present) ---
    public void exportToExcel(String filename) throws Exception {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Expenses");
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Category");
        header.createCell(1).setCellValue("Amount");
        header.createCell(2).setCellValue("Date");
        int rowNum = 1;
        for (Expense e : expenses) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(e.getCategory());
            row.createCell(1).setCellValue(e.getAmount());
            row.createCell(2).setCellValue(e.getDate().toString());
        }
        try (FileOutputStream fileOut = new FileOutputStream(filename)) {
            workbook.write(fileOut);
        }
        workbook.close();
    }

    public List<Expense> getExpenses() {
        return expenses;
    }
} 