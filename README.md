# 💸 Expense Tracker (Java Console App)

A beginner-friendly Java project to help you track your daily expenses, categorized and saved to a text file. Built with core Java features like `ArrayList`, `LocalDate`, file I/O, and method overloading.

---

## 📌 Features

- ✅ Add expenses with optional category and date
- ✅ View all recorded expenses
- ✅ Calculate total spent in a category
- ✅ Filter expenses by month
- ✅ Save & load data from a `.txt` file
- ✅ Handles default values for category and date
- ✅ Unicode ₹ symbol support for Indian currency

---

## 📁 File Structure

```
ExpenseTracker/
│
├── ExpenseTracker.java        // Main program (console menu)
├── ExpenseManager.java        // Handles expense operations
├── Expense.java               // Data class for individual expense entries
├── expense.txt                // Saved expenses (auto-created if not found)
├── README.md                  // Project documentation (this file)
```

---

## 🧠 Concepts Used

- Object-Oriented Programming (OOP)
- Constructor overloading & encapsulation
- Java Collections (`ArrayList`)
- File Handling (`BufferedReader`, `BufferedWriter`)
- Date API (`java.time.LocalDate`)
- Exception Handling
- Unicode formatting (₹ symbol)

---

## 🚀 How to Run

1. **Clone this repository:**
   ```bash
   git clone https://github.com/your-username/ExpenseTracker.git
   cd ExpenseTracker
   ```

2. **Compile the Java files:**
   ```bash
   javac ExpenseTracker.java ExpenseManager.java Expense.java
   ```

3. **Run the app:**
   ```bash
   java ExpenseTracker
   ```

> 💡 All expenses are saved to `expense.txt` in the project directory.

---

## ✍️ Expense Format (in expense.txt)

Each expense is stored as:
```
Category,Amount,Date
```

Example:
```
Food,150.0,2025-06-17
Transport,100.0,2025-06-16
```

---

## ⚠️ Notes

- Negative amounts are rejected with an error.
- If no category is entered, defaults to `"Miscellaneous"`.
- If no date is entered, defaults to `LocalDate.now()` (today).
- Uses Unicode ₹ (U+20B9) for currency — if it shows as `?`, your terminal font may not support it.

---

## 🛠️ Future Improvements

- [ ] Edit or delete expenses
- [ ] Monthly summary report
- [ ] GUI version using JavaFX or Swing
- [ ] JSON/CSV export
- [ ] Auto-save after every entry

---

## 👨‍💻 Author

Harshal John V  
Student at Loyola-ICAM College of Engineering and Technology  
Passionate about Java, software development, and building real-world apps

---

## 📄 License

This project is open-source and free to use under the [MIT License](LICENSE).
