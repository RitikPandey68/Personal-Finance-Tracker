// ===================================================
// FinanceFlow — Personal Finance Tracker JS
// ===================================================

// ===== DATA STORE =====
let state = {
  transactions: [],
  budgets: [],
  goals: [],
  currentPage: 'dashboard',
  currency: '₹',
  charts: {}
};

// Sample data
const sampleTransactions = [
  { id:1, type:'income', amount:85000, desc:'Monthly Salary - NTT Data', category:'salary', account:'hdfc', date:'2024-01-01', recurring:'monthly' },
  { id:2, type:'expense', amount:12500, desc:'Rent - 2BHK Apartment', category:'utilities', account:'hdfc', date:'2024-01-02', recurring:'monthly' },
  { id:3, type:'expense', amount:3200, desc:'BigBasket Grocery', category:'food', account:'paytm', date:'2024-01-05', recurring:'none' },
  { id:4, type:'expense', amount:1500, desc:'Uber - Office commute', category:'transport', account:'paytm', date:'2024-01-08', recurring:'none' },
  { id:5, type:'income', amount:5000, desc:'Freelance Project', category:'other', account:'sbi', date:'2024-01-10', recurring:'none' },
  { id:6, type:'expense', amount:2800, desc:'Zomato - Dining out', category:'food', account:'paytm', date:'2024-01-12', recurring:'none' },
  { id:7, type:'expense', amount:4999, desc:'Amazon Shopping', category:'shopping', account:'hdfc', date:'2024-01-14', recurring:'none' },
  { id:8, type:'expense', amount:800, desc:'Netflix + Spotify', category:'entertainment', account:'hdfc', date:'2024-01-15', recurring:'monthly' },
  { id:9, type:'expense', amount:1200, desc:'Gym Membership', category:'health', account:'hdfc', date:'2024-01-16', recurring:'monthly' },
  { id:10, type:'expense', amount:10000, desc:'SIP Investment', category:'investment', account:'sbi', date:'2024-01-20', recurring:'monthly' },
];

const sampleBudgets = [
  { category:'Food & Dining', icon:'fa-utensils', color:'#10b981', budgeted:8000, spent:6000 },
  { category:'Transport', icon:'fa-car', color:'#06b6d4', budgeted:3000, spent:1500 },
  { category:'Shopping', icon:'fa-bag-shopping', color:'#a855f7', budgeted:5000, spent:4999 },
  { category:'Entertainment', icon:'fa-film', color:'#f59e0b', budgeted:2000, spent:800 },
  { category:'Healthcare', icon:'fa-heart-pulse', color:'#ef4444', budgeted:3000, spent:1200 },
  { category:'Utilities', icon:'fa-bolt', color:'#6366f1', budgeted:15000, spent:12500 },
];

const sampleGoals = [
  { id:1, title:'Emergency Fund', emoji:'🛡️', target:300000, current:120000, deadline:'Dec 2024' },
  { id:2, title:'Europe Trip', emoji:'✈️', target:150000, current:65000, deadline:'Jun 2025' },
  { id:3, title:'New MacBook', emoji:'💻', target:120000, current:40000, deadline:'Mar 2025' },
  { id:4, title:'Home Down Payment', emoji:'🏠', target:2000000, current:350000, deadline:'Dec 2027' },
];

const categoryColors = {
  salary:'#10b981', food:'#f59e0b', transport:'#06b6d4', shopping:'#a855f7',
  utilities:'#6366f1', health:'#ef4444', entertainment:'#ec4899', investment:'#10b981', other:'#94a3b8'
};

const categoryIcons = {
  salary:'fa-indian-rupee-sign', food:'fa-utensils', transport:'fa-car', shopping:'fa-bag-shopping',
  utilities:'fa-bolt', health:'fa-heart-pulse', entertainment:'fa-film', investment:'fa-chart-line', other:'fa-circle'
};

// ===== INIT =====
document.addEventListener('DOMContentLoaded', () => {
  state.transactions = [...sampleTransactions];
  state.budgets = [...sampleBudgets];
  state.goals = [...sampleGoals];

  setupNavigation();
  setupSidebarToggle();
  loadDashboard();
  setDefaultDate();

  // Set current budget month
  const now = new Date();
  document.getElementById('budgetMonth').textContent =
    now.toLocaleString('default', { month: 'long', year: 'numeric' });
});

// ===== NAVIGATION =====
function setupNavigation() {
  document.querySelectorAll('.sidebar-link').forEach(link => {
    link.addEventListener('click', (e) => {
      e.preventDefault();
      const page = link.dataset.page;
      switchPage(page);
    });
  });
}

function switchPage(page) {
  // Hide all pages
  document.querySelectorAll('.page').forEach(p => p.classList.remove('active'));
  document.querySelectorAll('.sidebar-link').forEach(l => l.classList.remove('active'));

  // Show target
  const targetPage = document.getElementById(`page-${page}`);
  if (targetPage) targetPage.classList.add('active');

  const targetLink = document.querySelector(`.sidebar-link[data-page="${page}"]`);
  if (targetLink) targetLink.classList.add('active');

  // Update title
  const titles = {
    dashboard:'Dashboard', transactions:'Transactions', budget:'Budget', goals:'Goals',
    investments:'Investments', debt:'Debt Tracker', bills:'Bill Reminders',
    reports:'Reports', accounts:'Accounts', settings:'Settings'
  };
  document.getElementById('pageTitle').textContent = titles[page] || 'Dashboard';
  state.currentPage = page;

  // Load page specific content
  if (page === 'transactions') loadTransactions();
  if (page === 'budget') loadBudgetPage();
  if (page === 'goals') loadGoals();
  if (page === 'investments') loadInvestments();
  if (page === 'debt') loadDebt();
  if (page === 'bills') loadBills();
  if (page === 'reports') loadReports();
  if (page === 'accounts') loadAccounts();

  // Close sidebar on mobile
  if (window.innerWidth < 900) {
    document.getElementById('sidebar').classList.remove('open');
  }
}

// ===== SIDEBAR TOGGLE =====
function setupSidebarToggle() {
  document.getElementById('sidebarToggle').addEventListener('click', () => {
    document.getElementById('sidebar').classList.toggle('open');
  });
}

// ===== DASHBOARD =====
function loadDashboard() {
  const income = state.transactions.filter(t => t.type === 'income').reduce((s, t) => s + t.amount, 0);
  const expenses = state.transactions.filter(t => t.type === 'expense').reduce((s, t) => s + t.amount, 0);
  const savings = income - expenses;
  const investments = state.transactions.filter(t => t.category === 'investment').reduce((s, t) => s + t.amount, 0);

  animateNumber('totalIncome', income, '₹');
  animateNumber('totalExpenses', expenses, '₹');
  animateNumber('netSavings', savings, '₹');
  animateNumber('totalInvestments', investments * 15, '₹');

  renderRecentTransactions();
  renderBudgetOverview();
  initIncomeExpenseChart();
  initCategoryChart();

  // Health score
  const healthScore = Math.min(Math.round((savings / income) * 100 * 2.5), 100);
  document.getElementById('healthValue').textContent = `${healthScore}/100`;
  document.getElementById('healthFill').style.width = `${healthScore}%`;
}

function renderRecentTransactions() {
  const container = document.getElementById('recentTxList');
  const recent = state.transactions.slice(0, 6);
  container.innerHTML = recent.map(tx => `
    <div class="tx-item">
      <div class="tx-icon" style="background:${categoryColors[tx.category]}22;color:${categoryColors[tx.category]}">
        <i class="fas ${categoryIcons[tx.category]}"></i>
      </div>
      <div class="tx-details">
        <div class="tx-name">${tx.desc}</div>
        <div class="tx-category">${tx.category.replace(/_/g,' ')} • ${tx.date}</div>
      </div>
      <div class="tx-amount ${tx.type}">${tx.type === 'income' ? '+' : '-'}${fmt(tx.amount)}</div>
    </div>
  `).join('');
}

function renderBudgetOverview() {
  const container = document.getElementById('budgetOverviewList');
  container.innerHTML = state.budgets.slice(0,5).map(b => {
    const pct = Math.round((b.spent / b.budgeted) * 100);
    const color = pct >= 90 ? '#ef4444' : pct >= 70 ? '#f59e0b' : '#10b981';
    return `
      <div class="budget-item">
        <div class="budget-item-header">
          <span class="budget-cat">${b.category}</span>
          <span class="budget-amounts">${fmt(b.spent)} / ${fmt(b.budgeted)}</span>
        </div>
        <div class="budget-bar-track">
          <div class="budget-bar-fill" style="width:${Math.min(pct,100)}%;background:${color}"></div>
        </div>
      </div>
    `;
  }).join('');
}

// ===== CHARTS =====
function initIncomeExpenseChart() {
  const ctx = document.getElementById('incomeExpenseChart').getContext('2d');
  if (state.charts.incomeExpense) state.charts.incomeExpense.destroy();

  const months = ['Aug', 'Sep', 'Oct', 'Nov', 'Dec', 'Jan'];
  const incomeData = [72000, 78000, 85000, 80000, 88000, 90000];
  const expenseData = [55000, 60000, 58000, 65000, 62000, 58000];

  state.charts.incomeExpense = new Chart(ctx, {
    type: 'bar',
    data: {
      labels: months,
      datasets: [
        {
          label: 'Income',
          data: incomeData,
          backgroundColor: 'rgba(16,185,129,0.7)',
          borderRadius: 6,
          borderSkipped: false
        },
        {
          label: 'Expenses',
          data: expenseData,
          backgroundColor: 'rgba(239,68,68,0.7)',
          borderRadius: 6,
          borderSkipped: false
        }
      ]
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      plugins: {
        legend: { labels: { color: '#94a3b8', font: { family: 'Inter', size: 11 } } },
        tooltip: {
          callbacks: {
            label: ctx => ` ₹${ctx.parsed.y.toLocaleString('en-IN')}`
          }
        }
      },
      scales: {
        x: { grid: { color: 'rgba(255,255,255,0.03)' }, ticks: { color: '#475569', font: { size: 11 } } },
        y: { grid: { color: 'rgba(255,255,255,0.03)' }, ticks: { color: '#475569', font: { size: 10 }, callback: v => '₹' + (v/1000) + 'K' } }
      }
    }
  });
}

function initCategoryChart() {
  const ctx = document.getElementById('categoryChart').getContext('2d');
  if (state.charts.category) state.charts.category.destroy();

  const cats = {};
  state.transactions.filter(t => t.type === 'expense').forEach(t => {
    cats[t.category] = (cats[t.category] || 0) + t.amount;
  });

  const labels = Object.keys(cats).map(k => k.charAt(0).toUpperCase() + k.slice(1));
  const values = Object.values(cats);
  const colors = Object.keys(cats).map(k => categoryColors[k] || '#6366f1');

  state.charts.category = new Chart(ctx, {
    type: 'doughnut',
    data: {
      labels,
      datasets: [{ data: values, backgroundColor: colors, borderWidth: 2, borderColor: '#070710' }]
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      cutout: '65%',
      plugins: {
        legend: { display: false },
        tooltip: {
          callbacks: {
            label: ctx => ` ₹${ctx.parsed.toLocaleString('en-IN')}`
          }
        }
      }
    }
  });

  const legend = document.getElementById('pieLegend');
  legend.innerHTML = labels.map((l, i) => `
    <div class="pie-legend-item">
      <div class="pie-dot" style="background:${colors[i]}"></div>
      <span>${l}</span>
    </div>
  `).join('');
}

// ===== TRANSACTIONS PAGE =====
function loadTransactions() {
  renderTransactionTable(state.transactions);
  setupTxFilters();
}

function renderTransactionTable(txList) {
  const tbody = document.getElementById('txTableBody');
  tbody.innerHTML = txList.map(tx => `
    <tr>
      <td>${tx.date}</td>
      <td>
        <div style="display:flex;align-items:center;gap:0.75rem">
          <div style="width:30px;height:30px;border-radius:6px;display:flex;align-items:center;justify-content:center;background:${categoryColors[tx.category]}22;color:${categoryColors[tx.category]};font-size:0.75rem">
            <i class="fas ${categoryIcons[tx.category]}"></i>
          </div>
          ${tx.desc}
        </div>
      </td>
      <td><span style="background:${categoryColors[tx.category]}22;color:${categoryColors[tx.category]};padding:0.2rem 0.6rem;border-radius:50px;font-size:0.7rem;font-weight:600">${tx.category}</span></td>
      <td>${tx.account.toUpperCase()}</td>
      <td><span style="color:${tx.type==='income'?'var(--color-green)':'var(--color-red)';};font-weight:600;font-family:'Space Grotesk',sans-serif">${tx.type==='income'?'+':'-'}${fmt(tx.amount)}</span></td>
      <td>
        <button onclick="deleteTx(${tx.id})" style="background:none;border:none;color:var(--text-muted);cursor:pointer;font-size:0.85rem" title="Delete"><i class="fas fa-trash"></i></button>
      </td>
    </tr>
  `).join('');
}

function setupTxFilters() {
  ['txSearch','txTypeFilter','txCategoryFilter','txMonthFilter'].forEach(id => {
    const el = document.getElementById(id);
    if (el) el.addEventListener('input', filterTransactions);
  });
}

function filterTransactions() {
  const search = document.getElementById('txSearch').value.toLowerCase();
  const type = document.getElementById('txTypeFilter').value;
  const cat = document.getElementById('txCategoryFilter').value;

  const filtered = state.transactions.filter(tx =>
    (!search || tx.desc.toLowerCase().includes(search)) &&
    (!type || tx.type === type) &&
    (!cat || tx.category === cat)
  );
  renderTransactionTable(filtered);
}

function deleteTx(id) {
  state.transactions = state.transactions.filter(t => t.id !== id);
  loadTransactions();
  renderRecentTransactions();
  showToast('Transaction deleted', 'info');
}

// ===== ADD TRANSACTION MODAL =====
function openAddTransaction() {
  document.getElementById('addTxModal').style.display = 'flex';
  setDefaultDate();
}

function setDefaultDate() {
  const dateEl = document.getElementById('txDate');
  if (dateEl) dateEl.value = new Date().toISOString().split('T')[0];
}

let txType = 'income';
function setTxType(type) {
  txType = type;
  document.querySelectorAll('.type-btn').forEach(b => b.classList.remove('active'));
  document.getElementById(`type${type.charAt(0).toUpperCase()+type.slice(1)}`).classList.add('active');
}

function addTransaction() {
  const amount = parseFloat(document.getElementById('txAmount').value);
  const desc = document.getElementById('txDesc').value.trim();
  const category = document.getElementById('txCategory').value;
  const account = document.getElementById('txAccount').value;
  const date = document.getElementById('txDate').value;
  const recurring = document.getElementById('txRecurring').value;

  if (!amount || !desc) {
    showToast('Please fill amount and description', 'error');
    return;
  }

  const newTx = {
    id: Date.now(),
    type: txType,
    amount, desc, category, account, date, recurring
  };

  state.transactions.unshift(newTx);
  document.getElementById('addTxModal').style.display = 'none';
  document.getElementById('txAmount').value = '';
  document.getElementById('txDesc').value = '';

  loadDashboard();
  if (state.currentPage === 'transactions') loadTransactions();
  showToast('Transaction added!', 'success');
}

// ===== BUDGET PAGE =====
function loadBudgetPage() {
  const totalBudget = state.budgets.reduce((s, b) => s + b.budgeted, 0);
  const totalSpent = state.budgets.reduce((s, b) => s + b.spent, 0);
  document.getElementById('totalBudget').textContent = fmt(totalBudget);
  document.getElementById('totalSpent').textContent = fmt(totalSpent);
  document.getElementById('totalRemaining').textContent = fmt(totalBudget - totalSpent);

  const grid = document.getElementById('budgetCardGrid');
  grid.innerHTML = state.budgets.map(b => {
    const pct = Math.round((b.spent / b.budgeted) * 100);
    const color = pct >= 90 ? '#ef4444' : pct >= 70 ? '#f59e0b' : b.color;
    return `
      <div class="budget-card glass-card">
        <div class="budget-card-header">
          <div style="display:flex;align-items:center;gap:0.75rem">
            <div class="budget-card-icon" style="background:${b.color}22;color:${b.color}">
              <i class="fas ${b.icon}"></i>
            </div>
            <span class="budget-card-name">${b.category}</span>
          </div>
          <span class="budget-card-pct" style="color:${color}">${pct}%</span>
        </div>
        <div style="display:flex;justify-content:space-between;font-size:0.8rem;color:var(--text-muted);margin-bottom:0.6rem">
          <span>Spent: ${fmt(b.spent)}</span>
          <span>Budget: ${fmt(b.budgeted)}</span>
        </div>
        <div class="budget-bar-track">
          <div class="budget-bar-fill" style="width:${Math.min(pct,100)}%;background:${color}"></div>
        </div>
        <div style="font-size:0.75rem;color:${color};margin-top:0.4rem">
          ${pct >= 100 ? '⚠️ Over budget!' : `${fmt(b.budgeted - b.spent)} remaining`}
        </div>
      </div>
    `;
  }).join('');
}

function openAddBudget() { showToast('Add Budget feature coming soon!', 'info'); }

// ===== GOALS =====
function loadGoals() {
  const grid = document.getElementById('goalsGrid');
  grid.innerHTML = state.goals.map(g => {
    const pct = Math.round((g.current / g.target) * 100);
    return `
      <div class="goal-card glass-card">
        <div class="goal-icon">${g.emoji}</div>
        <div class="goal-title">${g.title}</div>
        <div class="goal-sub">Target: ${fmt(g.target)}</div>
        <div class="goal-amounts">
          <span class="goal-current">${fmt(g.current)} saved</span>
          <span class="goal-target">of ${fmt(g.target)}</span>
        </div>
        <div class="goal-bar-track">
          <div class="goal-bar-fill" style="width:${pct}%"></div>
        </div>
        <div class="goal-pct">${pct}% complete</div>
        <div class="goal-deadline">🗓️ Target: ${g.deadline}</div>
      </div>
    `;
  }).join('');
}

function openAddGoal() { showToast('Add Goal feature coming soon!', 'info'); }

// ===== INVESTMENTS =====
function loadInvestments() {
  const holdings = [
    { ticker:'RELIANCE', name:'Reliance Industries', value:45000, return:'+18.5%', pos:true },
    { ticker:'TCS', name:'Tata Consultancy', value:38000, return:'+12.3%', pos:true },
    { ticker:'INFY', name:'Infosys Limited', value:22000, return:'-3.2%', pos:false },
    { ticker:'HDFC', name:'HDFC Bank', value:31000, return:'+8.7%', pos:true },
    { ticker:'MIDCAP', name:'Motilal MidCap 150', value:15000, return:'+22.1%', pos:true },
    { ticker:'GOLDBEES', name:'Gold ETF', value:10000, return:'+6.4%', pos:true },
  ];

  document.getElementById('holdingsList').innerHTML = holdings.map(h => `
    <div class="holding-item">
      <span class="holding-ticker">${h.ticker}</span>
      <span class="holding-name">${h.name}</span>
      <span class="holding-value">${fmt(h.value)}</span>
      <span class="holding-return ${h.pos?'pos':'neg'}">${h.return}</span>
    </div>
  `).join('');

  const ctx = document.getElementById('portfolioChart').getContext('2d');
  if (state.charts.portfolio) state.charts.portfolio.destroy();
  state.charts.portfolio = new Chart(ctx, {
    type: 'doughnut',
    data: {
      labels: holdings.map(h => h.ticker),
      datasets: [{ data: holdings.map(h => h.value), backgroundColor: ['#6366f1','#a855f7','#10b981','#06b6d4','#f59e0b','#ec4899'], borderWidth: 2, borderColor: '#070710' }]
    },
    options: { responsive:true, maintainAspectRatio:false, cutout:'60%', plugins:{legend:{labels:{color:'#94a3b8',font:{size:10}}}} }
  });
}

// ===== DEBT =====
function loadDebt() {
  const debts = [
    { name:'Home Loan - SBI', type:'Home Loan', total:3000000, remaining:2100000, emi:28000, rate:'7.5%', tenure:'15 years' },
    { name:'Car Loan - HDFC', type:'Vehicle Loan', total:600000, remaining:320000, emi:12500, rate:'9.2%', tenure:'4 years' },
    { name:'Credit Card - ICICI', type:'Credit Card', total:0, remaining:45000, emi:45000, rate:'36%', tenure:'Revolving' },
  ];

  document.getElementById('debtGrid').innerHTML = debts.map(d => {
    const pct = Math.round(((d.total - d.remaining) / Math.max(d.total, 1)) * 100);
    return `
      <div class="debt-card glass-card">
        <div class="debt-name">${d.name}</div>
        <div class="debt-type">${d.type}</div>
        <div class="debt-stats">
          <div><div class="debt-stat-label">Outstanding</div><div class="debt-stat-value">${fmt(d.remaining)}</div></div>
          <div><div class="debt-stat-label">EMI</div><div class="debt-stat-value debt-emi">${fmt(d.emi)}</div></div>
          <div><div class="debt-stat-label">Interest Rate</div><div class="debt-stat-value">${d.rate}</div></div>
          <div><div class="debt-stat-label">Tenure</div><div class="debt-stat-value">${d.tenure}</div></div>
        </div>
        <div class="budget-bar-track" style="margin-top:0.5rem">
          <div class="budget-bar-fill" style="width:${pct}%;background:var(--gradient-green)"></div>
        </div>
        <div style="font-size:0.75rem;color:var(--text-muted);margin-top:0.4rem">${pct}% paid off</div>
      </div>
    `;
  }).join('');
}

// ===== BILLS =====
function loadBills() {
  const bills = [
    { name:'SBI Home Loan EMI', amount:28000, due:'Jan 5', dueStatus:'due-soon', badge:'Due Today', badgeClass:'red' },
    { name:'Electricity Bill', amount:1800, due:'Jan 8', dueStatus:'due-soon', badge:'3 Days', badgeClass:'red' },
    { name:'Netflix Subscription', amount:499, due:'Jan 15', dueStatus:'upcoming', badge:'9 Days', badgeClass:'orange' },
    { name:'Gym Membership', amount:1200, due:'Jan 16', dueStatus:'upcoming', badge:'10 Days', badgeClass:'orange' },
    { name:'Internet Bill - ACT', amount:999, due:'Jan 20', dueStatus:'upcoming', badge:'14 Days', badgeClass:'orange' },
    { name:'Life Insurance SIP', amount:5000, due:'Dec 28', dueStatus:'paid', badge:'Paid ✓', badgeClass:'green' },
  ];

  document.getElementById('billsGrid').innerHTML = bills.map(b => `
    <div class="bill-card glass-card ${b.dueStatus}">
      <div class="bill-header">
        <div>
          <div class="bill-name">${b.name}</div>
          <div class="bill-due">Due: ${b.due}</div>
        </div>
        <span class="bill-badge ${b.badgeClass}">${b.badge}</span>
      </div>
      <div class="bill-amount">${fmt(b.amount)}</div>
    </div>
  `).join('');
}

// ===== REPORTS =====
function loadReports() {
  // Trend chart
  const ctx1 = document.getElementById('reportTrendChart').getContext('2d');
  if (state.charts.reportTrend) state.charts.reportTrend.destroy();
  state.charts.reportTrend = new Chart(ctx1, {
    type:'line',
    data:{
      labels:['Jul','Aug','Sep','Oct','Nov','Dec','Jan'],
      datasets:[
        {label:'Income',data:[65000,70000,68000,75000,80000,85000,90000],borderColor:'#10b981',tension:0.4,fill:true,backgroundColor:'rgba(16,185,129,0.05)'},
        {label:'Expenses',data:[52000,55000,48000,60000,62000,58000,55000],borderColor:'#ef4444',tension:0.4,fill:true,backgroundColor:'rgba(239,68,68,0.05)'}
      ]
    },
    options:{responsive:true,maintainAspectRatio:false,plugins:{legend:{labels:{color:'#94a3b8',font:{size:10}}}},scales:{x:{grid:{color:'rgba(255,255,255,0.03)'},ticks:{color:'#475569'}},y:{grid:{color:'rgba(255,255,255,0.03)'},ticks:{color:'#475569',callback:v=>'₹'+(v/1000)+'K'}}}}
  });

  // Savings rate chart
  const ctx2 = document.getElementById('savingsRateChart').getContext('2d');
  if (state.charts.savingsRate) state.charts.savingsRate.destroy();
  state.charts.savingsRate = new Chart(ctx2, {
    type:'bar',
    data:{labels:['Jul','Aug','Sep','Oct','Nov','Dec','Jan'],datasets:[{label:'Savings %',data:[20,21,29,20,22,32,39],backgroundColor:'rgba(99,102,241,0.7)',borderRadius:6}]},
    options:{responsive:true,maintainAspectRatio:false,plugins:{legend:{labels:{color:'#94a3b8',font:{size:10}}}},scales:{x:{grid:{color:'rgba(255,255,255,0.03)'},ticks:{color:'#475569'}},y:{grid:{color:'rgba(255,255,255,0.03)'},ticks:{color:'#475569',callback:v=>v+'%'},suggestedMax:50}}}
  });

  document.getElementById('taxGrid').innerHTML = [
    {label:'Gross Income (FY)', value:'₹10,80,000'},
    {label:'Total Deductions (80C)', value:'₹1,50,000'},
    {label:'Standard Deduction', value:'₹50,000'},
    {label:'Net Taxable Income', value:'₹8,80,000'},
    {label:'Tax Liability (est.)', value:'₹93,000'},
    {label:'TDS Deducted', value:'₹85,000'},
  ].map(t => `
    <div class="tax-item">
      <div class="tax-label">${t.label}</div>
      <div class="tax-value">${t.value}</div>
    </div>
  `).join('');
}

// ===== ACCOUNTS =====
function loadAccounts() {
  const accounts = [
    { bank:'HDFC Bank', type:'Savings Account', number:'**** **** 4521', balance:45230, color:'#10b981' },
    { bank:'State Bank of India', type:'Savings Account', number:'**** **** 8834', balance:12800, color:'#6366f1' },
    { bank:'Paytm Wallet', type:'Digital Wallet', number:'***** 9876', balance:3450, color:'#06b6d4' },
    { bank:'Cash in Hand', type:'Physical Cash', number:'—', balance:5000, color:'#f59e0b' },
  ];

  document.getElementById('accountsGrid').innerHTML = accounts.map(a => `
    <div class="account-card glass-card" style="border-top:3px solid ${a.color}">
      <div class="account-bank"><i class="fas fa-building-columns" style="color:${a.color}"></i>${a.bank}</div>
      <div style="font-size:0.75rem;color:var(--text-muted);margin-bottom:0.5rem">${a.type}</div>
      <div class="account-number">${a.number}</div>
      <div class="account-balance-label">Available Balance</div>
      <div class="account-balance" style="color:${a.color}">${fmt(a.balance)}</div>
    </div>
  `).join('');
}

// ===== UTILITIES =====
function fmt(amount) {
  return '₹' + amount.toLocaleString('en-IN');
}

function animateNumber(id, value, prefix = '') {
  const el = document.getElementById(id);
  let current = 0;
  const step = value / 60;
  const interval = setInterval(() => {
    current = Math.min(current + step, value);
    el.textContent = prefix + Math.round(current).toLocaleString('en-IN');
    if (current >= value) clearInterval(interval);
  }, 16);
}

// ===== TOAST =====
function showToast(msg, type = 'info') {
  const existing = document.querySelector('.toast');
  if (existing) existing.remove();
  const colors = { success:'#10b981', error:'#ef4444', info:'#6366f1', warning:'#f59e0b' };
  const toast = document.createElement('div');
  toast.className = 'toast';
  toast.style.cssText = `position:fixed;bottom:2rem;right:2rem;z-index:9999;background:#0d0d1a;border:1px solid ${colors[type]}40;border-left:3px solid ${colors[type]};border-radius:12px;padding:0.875rem 1.5rem;font-size:0.875rem;color:#f1f5f9;box-shadow:0 8px 32px rgba(0,0,0,0.5);animation:slideIn 0.3s ease;backdrop-filter:blur(20px)`;
  toast.textContent = msg;
  document.body.appendChild(toast);
  setTimeout(() => toast.remove(), 3500);
}

const style = document.createElement('style');
style.textContent = '@keyframes slideIn{from{opacity:0;transform:translateX(100%)}to{opacity:1;transform:translateX(0)}}';
document.head.appendChild(style);
