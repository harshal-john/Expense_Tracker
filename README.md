# 💸 Expense Tracker

A modern expense tracking application with a **web-based frontend** and **Java backend** classes.
Track your daily expenses with a beautiful, responsive interface and robust file-based persistence.

---

## 🗺️ Table of Contents

* [Features](#-features)
* [Project Structure](#-project-structure)
* [Technology Stack](#-technology-stack)
* [How to Run](#-how-to-run)
* [Data Format](#-data-format)
* [User Interface Features](#-user-interface-features)
* [Future Enhancements](#-future-enhancements)
* [Author](#-author)
* [License](#-license)

---

## 📌 Features

### **Core Functionality**

* ✅ Add Expenses — Add new expenses with category, amount, and date
* ✅ Edit & Delete — Modify or remove existing expense entries
* ✅ View All Expenses — Display expenses in a clean, sortable table
* ✅ Real‑time Analytics — Totals, category breakdown, and monthly summaries

### **Advanced Features**

* ✅ Multiple Sort Options — Date, Amount, Category, and Category + Date
* ✅ Advanced Filtering — Category, Month, Amount Range
* ✅ File Management — Upload, merge, and export expense files
* ✅ Local Storage — Browser‑based persistence for offline access
* ✅ Responsive Design — Works beautifully across desktop and mobile

### **Data Persistence**

* ✅ File‑based Storage — Save expenses to `expense.txt` in **CSV** format
* ✅ Excel Support — Import `.xlsx` files via [SheetJS](https://sheetjs.com/)
* ✅ Auto‑save — Automatic save to browser `localStorage`
* ✅ Export Functionality — Export your data as `expense.txt`

---

## 📁 Project Structure

```
Expense_Tracker/
├── frontend/
│   ├── index.html            # Main web interface
│   ├── style.css             # Modern, responsive styling
│   └── script.js             # Frontend logic and interactions
├── models/
│   ├── Expense.java          # Expense data class
│   └── ExpenseManager.java   # Business logic and file I/O
└── README.md                # Project documentation
```

---

## ⚡️ Technology Stack

* **Backend**: Java 8
* **Frontend**: HTML5, CSS3, JavaScript
* **Storage**: File I/O (.txt), Local Storage (browser), Excel (.xlsx via SheetJS)

---

## 🚀 How to Run

### 🌐 Web Interface (Recommended)

1. **Open the frontend:**

   ```bash
   cd frontend
   # Open index.html in your browser
   # OR serve it with a local server:
   python -m http.server 8000
   ```

   Then visit **[http://localhost:8000](http://localhost:8000)**.

2. **Start Tracking Expenses:**

   * Add expenses via the form
   * Use filters/sorting
   * Upload existing `.txt` files
   * Export data when needed

---

### ☕️ Java Backend (For Development)

1. **Compile Java classes:**

   ```bash
   javac models/*.java
   ```
2. **Use in your Java projects:**

   ```java
   import models.Expense;
   import models.ExpenseManager;

   ExpenseManager manager = new ExpenseManager();
   manager.loadFromFile("expense.txt");
   // Add your custom logic here
   ```

---

## 🗂️ Data Format

Each expense is stored as:

```
Category,Amount,Date
```

### **Example Entries**

```
Food,150.50,2024-01-15
Transport,75.25,2024-01-16
Entertainment,200.00,2024-01-17
```

### **Default Values**

* Category: `Miscellaneous`
* Date: Current Date
* Amount: Must be a positive number

---

## 🎨 User Interface Features

* ✅ **Dashboard** — Real‑time totals, category‑wise analytics
* ✅ **Modern Layout** — Responsive across mobile, tablet, and desktop
* ✅ **Sticky Add Expense Form** — Always available for quick entry
* ✅ **Advanced Filters** — Category, date range, and amount range
* ✅ **File Operations** — Drag‑and‑drop upload, merging, and downloading files
* ✅ **Keyboard Navigation** — Fully accessible interface

---

## 🛠️ Future Enhancements

* ☁️ **Cloud Sync** — Backend server for data synchronization
* 👥 **User Accounts** — Multiple user profiles and authentication
* 📈 **Detailed Reports** — Charts and graphs for deeper analytics
* 💰 **Budget Tracking** — Set and track category‑wise or total monthly limits
* 🖼️ **Receipt Upload** — Image uploads and OCR for receipts
* 📱 **Native Mobile App** — Build mobile app for Expense Tracker

---

## 👨‍💻 Author

**Harshal John V**
Student, Loyola‑ICAM College of Engineering and Technology
Passionate about Java, software development, and building real‑world applications.

**Email**: harshaljohnv@gmail.com
**LinkedIn**: https://www.linkedin.com/in/harshaljohn

---

## 📄 License

This project is open‑source and available under the [MIT License](LICENSE).
MIT License

Copyright 2025 HARSHAL JOHN V

Permission is hereby granted, free of charge, to any person obtaining a copy 
of this software and associated documentation files (the "Software"), to 
deal in the Software without restriction, including without limitation the 
rights to use, copy, modify, merge, publish, distribute, sublicense, and/or 
sell copies of the Software, and to permit persons to whom the Software is 
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included 
in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS 
OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL 
THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR 
OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING 
FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS 
IN THE SOFTWARE.
