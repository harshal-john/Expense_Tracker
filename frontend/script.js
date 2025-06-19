let expenses = [];
let filteredExpenses = [];
let editingIndex = null;

const form = document.getElementById('expense-form');
const tableBody = document.querySelector('#expenses-table tbody');
const sortSelect = document.getElementById('sort-select');
const filterCategory = document.getElementById('filter-category');
const filterMonth = document.getElementById('filter-month');
const filterMin = document.getElementById('filter-min');
const filterMax = document.getElementById('filter-max');
const applyFiltersBtn = document.getElementById('apply-filters');
const clearFiltersBtn = document.getElementById('clear-filters');
const totalAmountDiv = document.getElementById('total-amount');
const totalByCategoryDiv = document.getElementById('total-by-category');
const monthlySummaryDiv = document.getElementById('monthly-summary');
const averageDailyDiv = document.getElementById('average-daily');

// --- File Management Logic ---
const uploadFileInput = document.getElementById('upload-file');
const uploadBtn = document.getElementById('upload-btn');
const exportBtn = document.getElementById('export-btn');
const fileMsg = document.getElementById('file-message');

// Load expenses from localStorage on app start
window.addEventListener('DOMContentLoaded', () => {
    const saved = localStorage.getItem('expenses');
    if (saved) {
        try {
            expenses = JSON.parse(saved);
            applyFiltersAndSort();
            fileMsg.textContent = `Loaded ${expenses.length} entries from local storage.`;
            fileMsg.style.color = '#388e3c';
        } catch (e) {
            expenses = [];
        }
    }
});

// Upload/merge new txt/xlsx file
uploadBtn.addEventListener('click', async () => {
    const file = uploadFileInput.files[0];
    if (!file) {
        fileMsg.textContent = 'Please select a .txt or .xlsx file to upload.';
        fileMsg.style.color = '#e53935';
        return;
    }
    const ext = file.name.split('.').pop().toLowerCase();
    let added = 0;
    if (ext === 'txt') {
        const text = await file.text();
        const lines = text.split(/\r?\n/);
        for (const line of lines) {
            if (!line.trim()) continue;
            const parts = line.split(',');
            if (parts.length !== 3) continue;
            const cat = parts[0].trim();
            const amt = parseFloat(parts[1].trim());
            if (isNaN(amt) || amt < 0) continue;
            let date = parts[2].trim();
            if (!/^\d{4}-\d{2}-\d{2}$/.test(date)) date = new Date().toISOString().slice(0,10);
            expenses.push({ category: cat, amount: amt, date });
            added++;
        }
    } else if (ext === 'xlsx') {
        if (typeof XLSX === 'undefined') {
            fileMsg.textContent = 'Excel upload requires SheetJS (XLSX) library.';
            fileMsg.style.color = '#e53935';
            return;
        }
        const data = await file.arrayBuffer();
        const workbook = XLSX.read(data, { type: 'array' });
        const sheet = workbook.Sheets[workbook.SheetNames[0]];
        const rows = XLSX.utils.sheet_to_json(sheet, { header: 1 });
        for (let i = 1; i < rows.length; i++) { // skip header
            const row = rows[i];
            if (!row || row.length < 3) continue;
            const cat = (row[0] || '').toString().trim();
            const amt = parseFloat(row[1]);
            if (isNaN(amt) || amt < 0) continue;
            let date = (row[2] || '').toString().trim();
            if (!/^\d{4}-\d{2}-\d{2}$/.test(date)) date = new Date().toISOString().slice(0,10);
            expenses.push({ category: cat, amount: amt, date });
            added++;
        }
    } else {
        fileMsg.textContent = 'Unsupported file type.';
        fileMsg.style.color = '#e53935';
        return;
    }
    fileMsg.textContent = `Merged ${added} entr${added === 1 ? 'y' : 'ies'} from upload.`;
    fileMsg.style.color = '#388e3c';
    uploadFileInput.value = '';
    localStorage.setItem('expenses', JSON.stringify(expenses));
    applyFiltersAndSort();
});

// Export current expenses as expense.txt
exportBtn.addEventListener('click', () => {
    const lines = expenses.map(e => `${e.category},${e.amount},${e.date}`);
    const blob = new Blob([lines.join('\n')], { type: 'text/plain' });
    const a = document.createElement('a');
    a.href = URL.createObjectURL(blob);
    a.download = 'expense.txt';
    a.click();
});

form.addEventListener('submit', function(e) {
    e.preventDefault();
    let category = document.getElementById('category').value.trim();
    const amount = parseFloat(document.getElementById('amount').value);
    let date = document.getElementById('date').value;
    if (!category) category = 'Malicious';
    if (!date) date = new Date().toISOString().slice(0,10);
    if (isNaN(amount)) return;
    expenses.push({ category, amount: parseFloat(amount.toFixed(2)), date });
    form.reset();
    localStorage.setItem('expenses', JSON.stringify(expenses));
    applyFiltersAndSort();
});

sortSelect.addEventListener('change', applyFiltersAndSort);
applyFiltersBtn.addEventListener('click', applyFiltersAndSort);
clearFiltersBtn.addEventListener('click', () => {
    filterCategory.value = '';
    filterMonth.value = '';
    filterMin.value = '';
    filterMax.value = '';
    applyFiltersAndSort();
});

function applyFiltersAndSort() {
    filteredExpenses = [...expenses];
    // Filtering
    if (filterCategory.value.trim()) {
        filteredExpenses = filteredExpenses.filter(e => e.category.toLowerCase() === filterCategory.value.trim().toLowerCase());
    }
    if (filterMonth.value) {
        const [year, month] = filterMonth.value.split('-');
        filteredExpenses = filteredExpenses.filter(e => {
            const d = new Date(e.date);
            return d.getFullYear() === +year && (d.getMonth() + 1) === +month;
        });
    }
    const min = parseFloat(filterMin.value);
    if (!isNaN(min)) {
        filteredExpenses = filteredExpenses.filter(e => e.amount >= min);
    }
    const max = parseFloat(filterMax.value);
    if (!isNaN(max)) {
        filteredExpenses = filteredExpenses.filter(e => e.amount <= max);
    }
    // Sorting
    switch (sortSelect.value) {
        case 'date-asc':
            filteredExpenses.sort((a, b) => new Date(a.date) - new Date(b.date));
            break;
        case 'date-desc':
            filteredExpenses.sort((a, b) => new Date(b.date) - new Date(a.date));
            break;
        case 'amount-asc':
            filteredExpenses.sort((a, b) => a.amount - b.amount);
            break;
        case 'amount-desc':
            filteredExpenses.sort((a, b) => b.amount - a.amount);
            break;
        case 'category':
            filteredExpenses.sort((a, b) => a.category.localeCompare(b.category, undefined, {sensitivity: 'base'}));
            break;
        case 'category-date':
            filteredExpenses.sort((a, b) => {
                const cat = a.category.localeCompare(b.category, undefined, {sensitivity: 'base'});
                if (cat !== 0) return cat;
                return new Date(a.date) - new Date(b.date);
            });
            break;
    }
    updateTable();
    updateAnalytics();
}

function updateTable() {
    tableBody.innerHTML = '';
    filteredExpenses.forEach((exp, idx) => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${exp.category}</td>
            <td>₹${exp.amount.toFixed(2)}</td>
            <td>${exp.date}</td>
            <td>
                <button class="actions-btn edit" onclick="editExpense(${expenses.indexOf(exp)})">Edit</button>
                <button class="actions-btn" onclick="deleteExpense(${expenses.indexOf(exp)})">Delete</button>
            </td>
        `;
        tableBody.appendChild(row);
    });
}

window.editExpense = function(index) {
    const exp = expenses[index];
    document.getElementById('category').value = exp.category;
    document.getElementById('amount').value = exp.amount;
    document.getElementById('date').value = exp.date;
    editingIndex = index;
    form.querySelector('button[type="submit"]').textContent = 'Update Expense';
}

window.deleteExpense = function(index) {
    if (confirm('Delete this expense?')) {
        expenses.splice(index, 1);
        applyFiltersAndSort();
    }
}

function updateAnalytics() {
    // Total amount
    const total = filteredExpenses.reduce((sum, e) => sum + e.amount, 0);
    totalAmountDiv.textContent = `Total: ₹${total.toFixed(2)}`;
    // Total by category (if filter applied)
    if (filterCategory.value.trim()) {
        totalByCategoryDiv.textContent = `Total in "${filterCategory.value}": ₹${total.toFixed(2)}`;
    } else {
        totalByCategoryDiv.textContent = '';
    }
    // Monthly summary and average daily
    if (filterMonth.value) {
        const [year, month] = filterMonth.value.split('-');
        const monthExpenses = filteredExpenses;
        const monthTotal = monthExpenses.reduce((sum, e) => sum + e.amount, 0);
        const days = new Set(monthExpenses.map(e => e.date));
        const avg = days.size ? (monthTotal / days.size) : 0;
        monthlySummaryDiv.textContent = `Month Total: ₹${monthTotal.toFixed(2)}`;
        averageDailyDiv.textContent = `Average Daily: ₹${avg.toFixed(2)}`;
    } else {
        monthlySummaryDiv.textContent = '';
        averageDailyDiv.textContent = '';
    }
}

// No backend: user must load or upload a file to start 