package com.financetracker.config;

import com.financetracker.model.*;
import com.financetracker.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final BudgetRepository budgetRepository;
    private final GoalRepository goalRepository;
    private final InvestmentRepository investmentRepository;
    private final DebtRepository debtRepository;
    private final BillRepository billRepository;
    private final TaxLineRepository taxLineRepository;
    private final IncomeSourceRepository incomeSourceRepository;
    private final ReconciliationRepository reconciliationRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository,
                           TransactionRepository transactionRepository,
                           BudgetRepository budgetRepository,
                           GoalRepository goalRepository,
                           InvestmentRepository investmentRepository,
                           DebtRepository debtRepository,
                           BillRepository billRepository,
                           TaxLineRepository taxLineRepository,
                           IncomeSourceRepository incomeSourceRepository,
                           ReconciliationRepository reconciliationRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.budgetRepository = budgetRepository;
        this.goalRepository = goalRepository;
        this.investmentRepository = investmentRepository;
        this.debtRepository = debtRepository;
        this.billRepository = billRepository;
        this.taxLineRepository = taxLineRepository;
        this.incomeSourceRepository = incomeSourceRepository;
        this.reconciliationRepository = reconciliationRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // 1. Seed User
        User user = userRepository.findByEmail("ritik@financeflow.com").orElseGet(() -> {
            User u = new User();
            u.setEmail("ritik@financeflow.com");
            u.setPassword(passwordEncoder.encode("SecurePass123!"));
            u.setFullName("Ritik Pandey");
            u.setPhone("+91 9876543210");
            u.setCurrency("INR");
            u.setEnabled(true);
            u.setEmailVerified(true);
            return userRepository.save(u);
        });

        // 2. Seed Transactions
        if (transactionRepository.count() == 0) {
            transactionRepository.saveAll(List.of(
                createTx(user, Transaction.TransactionType.INCOME, 85000, "Monthly Salary - NTT Data", "salary", "hdfc", LocalDate.now().minusDays(25), "monthly"),
                createTx(user, Transaction.TransactionType.EXPENSE, 12500, "Rent - 2BHK Apartment", "utilities", "hdfc", LocalDate.now().minusDays(24), "monthly"),
                createTx(user, Transaction.TransactionType.EXPENSE, 3200, "BigBasket Grocery", "food", "paytm", LocalDate.now().minusDays(20), "none"),
                createTx(user, Transaction.TransactionType.EXPENSE, 1500, "Uber - Office commute", "transport", "paytm", LocalDate.now().minusDays(18), "none"),
                createTx(user, Transaction.TransactionType.INCOME, 5000, "Freelance Project Milestone", "other", "sbi", LocalDate.now().minusDays(15), "none"),
                createTx(user, Transaction.TransactionType.EXPENSE, 2800, "Zomato - Dining out", "food", "paytm", LocalDate.now().minusDays(12), "none"),
                createTx(user, Transaction.TransactionType.EXPENSE, 4999, "Amazon Shopping", "shopping", "hdfc", LocalDate.now().minusDays(10), "none"),
                createTx(user, Transaction.TransactionType.EXPENSE, 800, "Netflix + Spotify", "entertainment", "hdfc", LocalDate.now().minusDays(8), "monthly"),
                createTx(user, Transaction.TransactionType.EXPENSE, 1200, "Gym Membership", "health", "hdfc", LocalDate.now().minusDays(6), "monthly"),
                createTx(user, Transaction.TransactionType.EXPENSE, 10000, "SIP Investment - Parag Parikh", "investment", "sbi", LocalDate.now().minusDays(2), "monthly")
            ));
        }

        // 3. Seed Budgets
        if (budgetRepository.count() == 0) {
            budgetRepository.saveAll(List.of(
                new Budget("Food & Dining", "fa-utensils", "#10b981", 8000.0, 6000.0, "January 2026"),
                new Budget("Transport", "fa-car", "#06b6d4", 3000.0, 1500.0, "January 2026"),
                new Budget("Shopping", "fa-bag-shopping", "#a855f7", 5000.0, 4999.0, "January 2026"),
                new Budget("Entertainment", "fa-film", "#f59e0b", 2000.0, 800.0, "January 2026"),
                new Budget("Healthcare", "fa-heart-pulse", "#ef4444", 3000.0, 1200.0, "January 2026"),
                new Budget("Utilities", "fa-bolt", "#6366f1", 15000.0, 12500.0, "January 2026")
            ));
        }

        // 4. Seed Goals
        if (goalRepository.count() == 0) {
            goalRepository.saveAll(List.of(
                new Goal("Emergency Buffer Fund", "🛡️", 300000.0, 120000.0, "Dec 2026", "Security"),
                new Goal("Europe Trip", "✈️", 150000.0, 65000.0, "Jun 2027", "Travel"),
                new Goal("New MacBook Pro M3", "💻", 120000.0, 40000.0, "Mar 2027", "Gadgets"),
                new Goal("Home Down Payment", "🏠", 2000000.0, 350000.0, "Dec 2028", "Real Estate")
            ));
        }

        // 5. Seed Investments
        if (investmentRepository.count() == 0) {
            investmentRepository.saveAll(List.of(
                new Investment("RELIANCE", "Reliance Industries Limited", "Stock", 15.0, 2530.0, 3000.0),
                new Investment("TCS", "Tata Consultancy Services", "Stock", 10.0, 3380.0, 3800.0),
                new Investment("INFY", "Infosys Limited", "Stock", 15.0, 1515.0, 1466.0),
                new Investment("HDFCBANK", "HDFC Bank Limited", "Stock", 20.0, 1425.0, 1550.0),
                new Investment("MIDCAP", "Motilal MidCap 150 ETF", "ETF", 250.0, 49.1, 60.0),
                new Investment("GOLDBEES", "Nippon India Gold ETF", "Gold", 160.0, 58.7, 62.5)
            ));
        }

        // 6. Seed Debts
        if (debtRepository.count() == 0) {
            debtRepository.saveAll(List.of(
                new Debt("HDFC Car Loan", 600000.0, 380000.0, 8.5, 12400.0, "5th of month"),
                new Debt("SBI Education Loan", 400000.0, 140000.0, 7.2, 8500.0, "10th of month"),
                new Debt("ICICI Credit Card (Zero Bal)", 50000.0, 0.0, 36.0, 0.0, "20th of month")
            ));
        }

        // 7. Seed Bills
        if (billRepository.count() == 0) {
            billRepository.saveAll(List.of(
                new Bill("Apartment Rent", 12500.0, "1st of month", "Housing", "monthly", true),
                new Bill("Electricity Bill (BESCOM)", 1850.0, "15th of month", "Utilities", "monthly", false),
                new Bill("Airtel Fiber Broadband", 1199.0, "18th of month", "Internet", "monthly", false),
                new Bill("Netflix 4K Premium", 649.0, "22nd of month", "Entertainment", "monthly", false)
            ));
        }

        // 8. Seed Tax Lines
        if (taxLineRepository.count() == 0) {
            taxLineRepository.saveAll(List.of(
                new TaxLine("EPF Employee Provident Fund (NTT Data Salary)", "80C", 48000.0, "Salary Slip", "FY 2024-25", true),
                new TaxLine("PPF Annual Public Provident Fund Deposit - SBI", "80C", 50000.0, "Bank Statement", "FY 2024-25", true),
                new TaxLine("Nippon India ELSS Tax Saver Mutual Fund SIP", "80C", 60000.0, "MF Statement", "FY 2024-25", true),
                new TaxLine("HDFC Ergo Optima Restore Health Insurance Premium", "80D", 25000.0, "Insurance Receipt", "FY 2024-25", true),
                new TaxLine("National Pension System (NPS Tier-1 Account)", "80CCD(1B)", 35000.0, "NPS Statement", "FY 2024-25", true),
                new TaxLine("HRA House Rent Allowance (2BHK Apartment)", "HRA", 67000.0, "Rent Agreement", "FY 2024-25", true)
            ));
        }

        // 9. Seed Income Sources
        if (incomeSourceRepository.count() == 0) {
            incomeSourceRepository.saveAll(List.of(
                new IncomeSource("Salary - NTT Data (Full-time)", "Salary", 50000.0, "Salary Slip", true),
                new IncomeSource("Freelance Software Projects", "Freelancing", 10000.0, "Bank Statement", true),
                new IncomeSource("Mutual Fund Dividends & Capital Gains", "Investments", 5000.0, "ITR-V Document", true)
            ));
        }

        // 10. Seed 52 Multi-Source Reconciliation Records (49 matched, 3 deliberate exceptions)
        if (reconciliationRepository.count() == 0) {
            for (int i = 1; i <= 49; i++) {
                int idNum = 1000 + i;
                double baseAmt = Math.round(1500.0 + ((i * 1370.0) % 45000.0));
                reconciliationRepository.save(new ReconciliationRecord(
                    "LED-" + idNum,
                    "Operational Ledger Batch Item #" + i,
                    "HDFC-UTR-782" + (100 + i),
                    "STRIPE-CH-99" + (10 + i),
                    baseAmt,
                    baseAmt,
                    0.0,
                    "Matched",
                    "100% 3-Way Verified (Ledger = Bank = Gateway)",
                    false
                ));
            }

            // Exception #1: Timing / Settlement Lag
            reconciliationRepository.save(new ReconciliationRecord(
                "LED-1042",
                "Stripe Global Card Payout (USD Batch)",
                "PENDING_CLEARING_T+2",
                "STRIPE-PO-491024",
                18500.0,
                0.0,
                18500.0,
                "Exception",
                "Settlement Lag: T+2 Weekend Bank Clearing Window",
                true
            ));

            // Exception #2: Gateway Fee Discrepancy
            reconciliationRepository.save(new ReconciliationRecord(
                "LED-1048",
                "Razorpay UPI Payout - Invoice #892",
                "ICICI-CMS-901148",
                "RZP-PAY-882048",
                4250.0,
                4207.50,
                42.50,
                "Exception",
                "Fee Discrepancy: ₹42.50 Gateway 1% MDR Deducted at Source",
                true
            ));

            // Exception #3: Missing Invoice / Unidentified Inflow
            reconciliationRepository.save(new ReconciliationRecord(
                "UNMAPPED-ENTRY",
                "Direct Bank Credit / Unidentified NEFT",
                "SBI-NEFT-8839201",
                "NONE_DIRECT_WIRE",
                24000.0,
                24000.0,
                0.0,
                "Exception",
                "Missing Invoice: Inflow not linked to any Client Ledger Bill",
                true
            ));
        }
    }

    private Transaction createTx(User user, Transaction.TransactionType type, double amount, String desc, String category, String account, LocalDate date, String recurring) {
        Transaction t = new Transaction();
        t.setUser(user);
        t.setType(type);
        t.setAmount(BigDecimal.valueOf(amount));
        t.setDescription(desc);
        t.setCategory(category);
        t.setAccount(account);
        t.setTransactionDate(date);
        t.setRecurring(Transaction.RecurringType.valueOf(recurring.toUpperCase()));
        t.setCurrency("INR");
        return t;
    }
}
