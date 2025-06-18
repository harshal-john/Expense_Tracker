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

form.addEventListener('submit', function(e) {
    e.preventDefault();
    const category = document.getElementById('category').value.trim();
    const amount = parseFloat(document.getElementById('amount').value);
    const date = document.getElementById('date').value;
    if (!category || isNaN(amount) || !date) return;
    const expense = { category, amount: parseFloat(amount.toFixed(2)), date };
    if (editingIndex !== null) {
        expenses[editingIndex] = expense;
        editingIndex = null;
        form.querySelector('button[type="submit"]').textContent = 'Add Expense';
    } else {
        expenses.push(expense);
    }
    form.reset();
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

// Initial render
applyFiltersAndSort(); 