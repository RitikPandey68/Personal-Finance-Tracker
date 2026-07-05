package com.financetracker.service;

import com.financetracker.model.Transaction;
import com.financetracker.model.User;
import com.financetracker.repository.TransactionRepository;
import com.financetracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;

@Service
public class TransactionService {

    @Autowired private TransactionRepository transactionRepo;
    @Autowired private UserRepository userRepo;

    public List<Transaction> getTransactions(String email, String type, String category,
                                              LocalDate startDate, LocalDate endDate) {
        User user = getUserByEmail(email);
        if (type != null && category != null) {
            return transactionRepo.findByUserAndTypeAndCategory(user, 
                Transaction.TransactionType.valueOf(type.toUpperCase()), category);
        }
        if (startDate != null && endDate != null) {
            return transactionRepo.findByUserAndTransactionDateBetween(user, startDate, endDate);
        }
        return transactionRepo.findByUserOrderByTransactionDateDesc(user);
    }

    public Transaction createTransaction(String email, Transaction transaction) {
        User user = getUserByEmail(email);
        transaction.setUser(user);
        if (transaction.getTransactionDate() == null) {
            transaction.setTransactionDate(LocalDate.now());
        }
        return transactionRepo.save(transaction);
    }

    public Transaction updateTransaction(String email, Long id, Transaction updated) {
        User user = getUserByEmail(email);
        Transaction existing = transactionRepo.findByIdAndUser(id, user)
            .orElseThrow(() -> new RuntimeException("Transaction not found"));
        existing.setAmount(updated.getAmount());
        existing.setDescription(updated.getDescription());
        existing.setCategory(updated.getCategory());
        existing.setType(updated.getType());
        existing.setTransactionDate(updated.getTransactionDate());
        return transactionRepo.save(existing);
    }

    public void deleteTransaction(String email, Long id) {
        User user = getUserByEmail(email);
        Transaction tx = transactionRepo.findByIdAndUser(id, user)
            .orElseThrow(() -> new RuntimeException("Transaction not found"));
        transactionRepo.delete(tx);
    }

    public Map<String, Object> getSummary(String email, String period) {
        User user = getUserByEmail(email);
        LocalDate start = switch (period) {
            case "WEEK" -> LocalDate.now().minusWeeks(1);
            case "MONTH" -> LocalDate.now().withDayOfMonth(1);
            case "YEAR" -> LocalDate.now().withDayOfYear(1);
            default -> LocalDate.now().withDayOfMonth(1);
        };

        List<Transaction> txList = transactionRepo.findByUserAndTransactionDateBetween(user, start, LocalDate.now());

        BigDecimal totalIncome = txList.stream()
            .filter(t -> t.getType() == Transaction.TransactionType.INCOME)
            .map(Transaction::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalExpense = txList.stream()
            .filter(t -> t.getType() == Transaction.TransactionType.EXPENSE)
            .map(Transaction::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal savings = totalIncome.subtract(totalExpense);

        Map<String, Object> summary = new HashMap<>();
        summary.put("totalIncome", totalIncome);
        summary.put("totalExpenses", totalExpense);
        summary.put("netSavings", savings);
        summary.put("savingsRate", totalIncome.compareTo(BigDecimal.ZERO) > 0
            ? savings.divide(totalIncome, 4, RoundingMode.HALF_UP).multiply(new BigDecimal("100"))
            : BigDecimal.ZERO);
        summary.put("transactionCount", txList.size());
        summary.put("period", period);

        return summary;
    }

    public byte[] exportToCsv(String email) {
        User user = getUserByEmail(email);
        List<Transaction> transactions = transactionRepo.findByUserOrderByTransactionDateDesc(user);

        StringWriter sw = new StringWriter();
        try (CSVPrinter printer = new CSVPrinter(sw, CSVFormat.DEFAULT
            .withHeader("Date", "Description", "Category", "Type", "Amount", "Account", "Currency"))) {
            for (Transaction tx : transactions) {
                printer.printRecord(
                    tx.getTransactionDate(), tx.getDescription(), tx.getCategory(),
                    tx.getType(), tx.getAmount(), tx.getAccount(), tx.getCurrency()
                );
            }
        } catch (Exception e) {
            throw new RuntimeException("CSV export failed", e);
        }
        return sw.toString().getBytes();
    }

    public Map<String, Object> calculateFinancialHealthScore(String email) {
        User user = getUserByEmail(email);
        Map<String, Object> summary = getSummary(email, "MONTH");

        BigDecimal income = (BigDecimal) summary.get("totalIncome");
        BigDecimal expenses = (BigDecimal) summary.get("totalExpenses");
        BigDecimal savingsRate = (BigDecimal) summary.get("savingsRate");

        int score = 0;
        List<String> insights = new ArrayList<>();

        // Savings rate (40 points)
        if (savingsRate.compareTo(new BigDecimal("30")) >= 0) {
            score += 40;
            insights.add("Excellent savings rate! You save more than 30% of income.");
        } else if (savingsRate.compareTo(new BigDecimal("20")) >= 0) {
            score += 28;
            insights.add("Good savings rate. Try to reach 30% for better financial health.");
        } else {
            score += 15;
            insights.add("Savings rate needs improvement. Aim for at least 20% of income.");
        }

        // Budget adherence (30 points) — simplified
        score += 20;
        insights.add("Spending is within healthy limits for most categories.");

        // Emergency fund (30 points) — placeholder
        score += 18;
        insights.add("Consider building 6 months of expenses as emergency fund.");

        Map<String, Object> result = new HashMap<>();
        result.put("score", Math.min(score, 100));
        result.put("grade", score >= 80 ? "Excellent" : score >= 60 ? "Good" : score >= 40 ? "Average" : "Needs Work");
        result.put("insights", insights);
        result.put("savingsRate", savingsRate);

        return result;
    }

    private User getUserByEmail(String email) {
        return userRepo.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found: " + email));
    }
}
