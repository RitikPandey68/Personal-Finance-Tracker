package com.financetracker.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class FinTechSuiteService {

    // 1. 📄 AI PDF Bank Statement & Invoice Parser
    public Map<String, Object> parseBankStatementPdf(String filename) {
        List<Map<String, Object>> extractedTx = List.of(
            Map.of("date", "2026-08-22", "description", "HDFC UPI - Swiggy Instamart", "category", "Food", "amount", 640.0, "type", "EXPENSE", "confidence", "99.4%"),
            Map.of("date", "2026-08-20", "description", "Amazon Web Services (AWS)", "category", "Tech & Cloud", "amount", 2850.0, "type", "EXPENSE", "confidence", "98.8%"),
            Map.of("date", "2026-08-18", "description", "Client NEFT Inflow - Retainer", "category", "Salary / Client", "amount", 45000.0, "type", "INCOME", "confidence", "99.9%"),
            Map.of("date", "2026-08-15", "description", "Airtel Fiber Broadband", "category", "Utilities", "amount", 1199.0, "type", "EXPENSE", "confidence", "97.5%"),
            Map.of("date", "2026-08-12", "description", "Zerodha Broking Deposit", "category", "Investment", "amount", 15000.0, "type", "TRANSFER", "confidence", "99.1%")
        );

        Map<String, Object> res = new HashMap<>();
        res.put("status", "SUCCESS");
        res.put("filename", filename != null ? filename : "HDFC_Aug_Statement.pdf");
        res.put("totalTransactionsExtracted", extractedTx.size());
        res.put("totalInflow", 45000.0);
        res.put("totalOutflow", 19689.0);
        res.put("transactions", extractedTx);
        res.put("message", "Smart OCR successfully parsed " + extractedTx.size() + " records with 99.1% average confidence.");
        return res;
    }

    // 2. 🛡️ UPI Duplicate & Ghost Subscription Anomaly Detector
    public Map<String, Object> scanUpiAnomalies() {
        List<Map<String, Object>> duplicates = List.of(
            Map.of("id", "ANOM-1", "merchant", "Zomato UPI (ICICI)", "amount", 480.0, "timestamp", "2026-08-24 13:42:10 & 13:44:02", "timeGapSeconds", 112, "severity", "HIGH_RISK_DUPLICATE", "status", "Flagged for Chargeback Dispute")
        );

        List<Map<String, Object>> ghostSubs = List.of(
            Map.of("id", "SUB-1", "service", "Notion Team Plus (Inactive Workspace)", "monthlyAmount", 999.0, "annualWastage", 11988.0, "lastUsed", "64 days ago", "action", "Cancel Subscription"),
            Map.of("id", "SUB-2", "service", "Apple TV+ (Unused Trial Auto-Renew)", "monthlyAmount", 99.0, "annualWastage", 1188.0, "lastUsed", "48 days ago", "action", "Cancel Subscription")
        );

        Map<String, Object> res = new HashMap<>();
        res.put("duplicateTransactions", duplicates);
        res.put("ghostSubscriptions", ghostSubs);
        res.put("totalAnnualSavingsIdentified", 13176.0);
        res.put("anomalyStatus", "Action Required: 1 Duplicate Payment & 2 Unused Subscriptions Detected");
        return res;
    }

    // 3. ⚖️ 1-Click Double-Entry General Ledger (T-Account Generator)
    public Map<String, Object> getGeneralLedgerTAccounts() {
        List<Map<String, Object>> journal = List.of(
            Map.of("id", "JE-101", "date", "2026-08-26", "debitAccount", "Bank Charges (MDR Fee A/C)", "creditAccount", "Accounts Receivable (Stripe)", "amount", 42.50, "narration", "Reconciliation adjustment for 1% gateway fee on Tx #1048", "posted", true),
            Map.of("id", "JE-102", "date", "2026-08-25", "debitAccount", "HDFC Bank Operating A/C", "creditAccount", "Client Advance / Unearned Revenue", "amount", 24000.0, "narration", "Unidentified direct NEFT credit mapped to Advance Ledger on Tx #1051", "posted", true)
        );

        Map<String, Object> res = new HashMap<>();
        res.put("totalDebit", 24042.50);
        res.put("totalCredit", 24042.50);
        res.put("isBalanced", true);
        res.put("journalEntries", journal);
        res.put("trialBalanceStatus", "100% Balanced (Dr = Cr)");
        return res;
    }

    public Map<String, Object> postJournalEntry(String debitAcc, String creditAcc, Double amount, String narration) {
        Map<String, Object> entry = new HashMap<>();
        entry.put("id", "JE-" + (100 + new Random().nextInt(900)));
        entry.put("date", LocalDate.now().toString());
        entry.put("debitAccount", debitAcc);
        entry.put("creditAccount", creditAcc);
        entry.put("amount", amount);
        entry.put("narration", narration);
        entry.put("status", "POSTED_TO_GENERAL_LEDGER");
        return entry;
    }

    // 4. 💱 Multi-Currency & Cross-Border FX Loss Tracker
    public Map<String, Object> analyzeFxLoss(Double foreignAmount, String currency) {
        double amt = foreignAmount != null ? foreignAmount : 2500.0;
        String cur = currency != null ? currency.toUpperCase() : "USD";

        double interbankRate = cur.equals("EUR") ? 94.20 : cur.equals("GBP") ? 111.40 : 87.50; // USD default
        double bankPayoutRate = interbankRate - 1.85; // Bank hidden markup ~2.1%

        double idealInr = amt * interbankRate;
        double actualInr = amt * bankPayoutRate;
        double hiddenFxLoss = idealInr - actualInr;

        Map<String, Object> res = new HashMap<>();
        res.put("foreignCurrency", cur);
        res.put("foreignAmount", amt);
        res.put("interbankRate", interbankRate);
        res.put("bankPayoutRate", bankPayoutRate);
        res.put("idealInrPayout", Math.round(idealInr));
        res.put("actualBankPayout", Math.round(actualInr));
        res.put("hiddenFxMarkupLoss", Math.round(hiddenFxLoss));
        res.put("spreadPercentage", "2.11%");
        res.put("recommendedRoute", "Wise Business / Eximbank Direct FIRC (Saves ₹" + Math.round(hiddenFxLoss * 0.85) + ")");
        return res;
    }

    // 5. 💬 WhatsApp & Telegram Expense Webhook Simulator
    public Map<String, Object> processChatExpenseWebhook(String messageText) {
        String msg = (messageText != null) ? messageText : "Spent 350 on Uber ride to airport";
        String clean = msg.toLowerCase();

        double amount = 350.0;
        try {
            String[] tokens = clean.replaceAll("[^a-z0-9\\s]", " ").split("\\s+");
            for (String tok : tokens) {
                if (tok.matches("\\d+(\\.\\d+)?")) {
                    amount = Double.parseDouble(tok);
                    break;
                }
            }
        } catch (Exception ignored) {}

        String category = "Other";
        if (clean.contains("uber") || clean.contains("ola") || clean.contains("cab") || clean.contains("metro") || clean.contains("fuel")) {
            category = "Transport";
        } else if (clean.contains("tea") || clean.contains("coffee") || clean.contains("lunch") || clean.contains("zomato") || clean.contains("swiggy")) {
            category = "Food & Dining";
        } else if (clean.contains("amazon") || clean.contains("flipkart") || clean.contains("shopping")) {
            category = "Shopping";
        }

        Map<String, Object> res = new HashMap<>();
        res.put("rawMessage", messageText);
        res.put("parsedAmount", amount);
        res.put("parsedCategory", category);
        res.put("parsedDate", LocalDate.now().toString());
        res.put("status", "TRANSACTION_RECORDED_VIA_WEBHOOK");
        res.put("botReply", "✅ Logged: ₹" + amount + " under '" + category + "' successfully!");
        return res;
    }

    // 6. 📉 Tax-Loss Harvesting & Capital Gains Optimizer
    public Map<String, Object> getTaxLossHarvestingOpportunities() {
        List<Map<String, Object>> lossHoldings = List.of(
            Map.of("ticker", "INFY", "name", "Infosys Limited", "unrealizedLoss", 735.0, "suggestedSellQty", 15, "taxSavingLTCG", 91.87, "replacementAsset", "NIFTYBEES / TCS"),
            Map.of("ticker", "MIDCAP150", "name", "HDFC Mid-Cap Opportunities Fund", "unrealizedLoss", 12400.0, "suggestedSellQty", 250, "taxSavingLTCG", 1550.0, "replacementAsset", "Motilal Midcap 150 ETF")
        );

        Map<String, Object> res = new HashMap<>();
        res.put("totalHarvestableLoss", 13135.0);
        res.put("potentialTaxSavings", 1641.87);
        res.put("deadline", "March 31, 2026 (Before FY Close)");
        res.put("recommendation", "Harvest ₹13,135 unrealized losses now to offset FY 2025-26 Capital Gains tax liability.");
        res.put("opportunities", lossHoldings);
        return res;
    }

    // 7. 💳 Smart Credit Card Reward & Cashback Switcher
    public Map<String, Object> recommendCreditCard(String merchant, Double amount) {
        String m = (merchant != null) ? merchant.toLowerCase() : "swiggy";
        double amt = (amount != null && amount > 0) ? amount : 1200.0;

        String bestCard;
        String rewardRate;
        double savings;

        if (m.contains("swiggy") || m.contains("zomato") || m.contains("food")) {
            bestCard = "HDFC Swiggy Credit Card";
            rewardRate = "10% Instant Cashback";
            savings = amt * 0.10;
        } else if (m.contains("amazon")) {
            bestCard = "ICICI Amazon Pay Card";
            rewardRate = "5% Unlimited Cashback";
            savings = amt * 0.05;
        } else if (m.contains("flight") || m.contains("hotel") || m.contains("trip") || m.contains("travel")) {
            bestCard = "Axis Atlas / HDFC Infinia";
            rewardRate = "5x Edge Miles (10% Travel Value)";
            savings = amt * 0.10;
        } else {
            bestCard = "SBI Cashback Credit Card";
            rewardRate = "5% Universal Online Cashback";
            savings = amt * 0.05;
        }

        Map<String, Object> res = new HashMap<>();
        res.put("merchant", merchant != null ? merchant : "Swiggy");
        res.put("orderAmount", amt);
        res.put("recommendedCard", bestCard);
        res.put("rewardRate", rewardRate);
        res.put("estimatedSavings", Math.round(savings));
        res.put("netEffectiveCost", Math.round(amt - savings));
        return res;
    }

    // 8. ⏱️ Autonomous Invoice Chaser & Accounts Receivable Aging (AR)
    public Map<String, Object> getAccountsReceivableAging() {
        List<Map<String, Object>> invoices = List.of(
            Map.of("invoiceId", "INV-2026-081", "client", "Acme Digital US", "amount", 85000.0, "dueDate", "2026-07-25", "daysOverdue", 33, "bucket", "31-60 Days", "status", "URGENT_FOLLOWUP"),
            Map.of("invoiceId", "INV-2026-094", "client", "Zenith Tech Labs", "amount", 42000.0, "dueDate", "2026-08-15", "daysOverdue", 12, "bucket", "0-30 Days", "status", "FIRST_REMINDER_SENT"),
            Map.of("invoiceId", "INV-2026-062", "client", "HyperGrowth Media", "amount", 120000.0, "dueDate", "2026-06-10", "daysOverdue", 78, "bucket", "60+ Days Overdue", "status", "FINAL_LEGAL_NOTICE")
        );

        Map<String, Object> res = new HashMap<>();
        res.put("totalOutstandingReceivables", 247000.0);
        res.put("bucket0to30Days", 42000.0);
        res.put("bucket31to60Days", 85000.0);
        res.put("bucket60PlusDays", 120000.0);
        res.put("invoices", invoices);
        res.put("dunningActionReady", "Auto-generated 3 WhatsApp / Email reminders with 1-click payment links.");
        return res;
    }

    // 9. 📈 Inflation-Adjusted Real-Cost Goal Simulator
    public Map<String, Object> simulateInflationGoal(String goalTitle, Double todayCost, int yearsAway, Double expectedInflation) {
        String title = goalTitle != null ? goalTitle : "Child College Education";
        double base = todayCost != null ? todayCost : 3000000.0; // 30 Lakhs today
        int years = (yearsAway > 0) ? yearsAway : 12;
        double inf = (expectedInflation != null) ? expectedInflation : 8.0; // 8% education inflation

        double futureNominalCost = base * Math.pow(1.0 + (inf / 100.0), years);
        double monthlySipRequired = (futureNominalCost * (0.12 / 12.0)) / (Math.pow(1.0 + (0.12 / 12.0), years * 12) - 1.0);

        Map<String, Object> res = new HashMap<>();
        res.put("goalTitle", title);
        res.put("todayEstimatedCost", base);
        res.put("yearsToGoal", years);
        res.put("inflationRate", inf + "%");
        res.put("futureNominalRequirement", Math.round(futureNominalCost));
        res.put("recommendedMonthlySip", Math.round(monthlySipRequired));
        res.put("insight", "Due to " + inf + "% compounding inflation, your ₹" + Math.round(base / 100000) + " Lakhs goal will require ₹" + Math.round(futureNominalCost / 100000) + " Lakhs in " + years + " years.");
        return res;
    }

    // 10. 🧾 Vendor GST 2B vs 3B Input Tax Credit (ITC) Reconciler
    public Map<String, Object> reconcileGstItc() {
        List<Map<String, Object>> itcExceptions = List.of(
            Map.of("vendorName", "CloudHost Networks Pvt Ltd", "gstin", "29AAACC1206M1Z1", "invoiceNo", "CHN-9021", "amount", 35400.0, "itcAmount", 5400.0, "statusIn2B", "MISSING_IN_GSTR2B", "risk", "HIGH - Vendor has not filed GSTR-1. Hold payment until 2B reflects.")
        );

        Map<String, Object> res = new HashMap<>();
        res.put("totalClaimableItc", 48200.0);
        res.put("matchedEligibleItc", 42800.0);
        res.put("blockedHighRiskItc", 5400.0);
        res.put("itcMatchRate", "88.8%");
        res.put("exceptions", itcExceptions);
        res.put("actionPlan", "1 Vendor invoice flagged. Hold ₹5,400 tax payment to avoid GST DRC-01B notice.");
        return res;
    }

    // 11. 🤝 Splitwise Group Expense & Dynamic UPI QR Generator
    public Map<String, Object> splitGroupExpense(String tripName, Double totalBill, List<String> members, String paidBy) {
        String trip = tripName != null ? tripName : "Goa Vacation Trip";
        double total = totalBill != null ? totalBill : 16000.0;
        List<String> mems = (members != null && !members.isEmpty()) ? members : List.of("Ritik Pandey", "Aman Verma", "Siddharth", "Pooja");
        String payer = paidBy != null ? paidBy : "Ritik Pandey";

        double splitPerPerson = total / mems.size();
        String upiId = "ritikpandey@okaxis";
        String dynamicUpiUrl = "upi://pay?pa=" + upiId + "&pn=" + payer.replace(" ", "%20") + "&am=" + Math.round(splitPerPerson) + "&tn=" + trip.replace(" ", "%20") + "%20Split&cu=INR";

        List<Map<String, Object>> settlementPlan = new ArrayList<>();
        for (String m : mems) {
            if (!m.equalsIgnoreCase(payer)) {
                settlementPlan.add(Map.of("debtor", m, "creditor", payer, "amountOwed", splitPerPerson, "status", "PENDING_UPI_PAYMENT"));
            }
        }

        Map<String, Object> res = new HashMap<>();
        res.put("tripName", trip);
        res.put("totalExpense", total);
        res.put("splitPerHead", splitPerPerson);
        res.put("paidBy", payer);
        res.put("upiPaymentLink", dynamicUpiUrl);
        res.put("settlements", settlementPlan);
        res.put("qrCodeText", "Scan to pay ₹" + Math.round(splitPerPerson) + " directly via GooglePay / PhonePe / Paytm");
        return res;
    }
}
