package com.financetracker.chatbot;

import com.financetracker.chatbot.tools.*;
import com.financetracker.model.Transaction;
import com.financetracker.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
public class FinancialContextService {

    @Autowired private TransactionService transactionService;
    @Autowired private ExpenseTool expenseTool;
    @Autowired private BudgetTool budgetTool;
    @Autowired private InvestmentTool investmentTool;
    @Autowired private FinancialHealthTool healthTool;

    public Map<String, Object> collectUserFinancialContext(String userEmail) {
        List<Transaction> transactions = transactionService.getTransactions(userEmail, null, null, null, null);
        Map<String, Object> summary = transactionService.getSummary(userEmail, "MONTH");

        BigDecimal totalIncome = (BigDecimal) summary.getOrDefault("totalIncome", BigDecimal.ZERO);
        BigDecimal totalExpenses = (BigDecimal) summary.getOrDefault("totalExpenses", BigDecimal.ZERO);

        Map<String, Object> expenseAnalysis = expenseTool.analyzeExpenses(transactions);
        Map<String, Object> budgetStatus = budgetTool.evaluateBudgetStatus(totalIncome, totalExpenses);
        Map<String, Object> investmentAnalysis = investmentTool.analyzeInvestments(transactions);
        Map<String, Object> healthScore = healthTool.calculateHealthScore(totalIncome, totalExpenses);

        Map<String, Object> context = new HashMap<>();
        context.put("email", userEmail);
        context.put("totalIncome", totalIncome);
        context.put("totalExpenses", totalExpenses);
        context.put("netSavings", summary.get("netSavings"));
        context.put("savingsRate", summary.get("savingsRate"));
        context.put("expenseAnalysis", expenseAnalysis);
        context.put("budgetStatus", budgetStatus);
        context.put("investmentAnalysis", investmentAnalysis);
        context.put("healthScore", healthScore);
        context.put("transactionCount", transactions.size());

        return context;
    }
}
