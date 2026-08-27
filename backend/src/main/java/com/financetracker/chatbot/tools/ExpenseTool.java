package com.financetracker.chatbot.tools;

import com.financetracker.model.Transaction;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ExpenseTool {

    public Map<String, Object> analyzeExpenses(List<Transaction> transactions) {
        List<Transaction> expenses = transactions.stream()
                .filter(t -> t.getType() == Transaction.TransactionType.EXPENSE)
                .collect(Collectors.toList());

        BigDecimal totalExpense = expenses.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, BigDecimal> categoryBreakdown = new HashMap<>();
        for (Transaction t : expenses) {
            categoryBreakdown.put(t.getCategory(),
                    categoryBreakdown.getOrDefault(t.getCategory(), BigDecimal.ZERO).add(t.getAmount()));
        }

        String highestCategory = categoryBreakdown.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("None");

        BigDecimal highestCategoryAmount = categoryBreakdown.getOrDefault(highestCategory, BigDecimal.ZERO);

        Map<String, Object> result = new HashMap<>();
        result.put("totalExpense", totalExpense);
        result.put("count", expenses.size());
        result.put("categoryBreakdown", categoryBreakdown);
        result.put("highestCategory", highestCategory);
        result.put("highestCategoryAmount", highestCategoryAmount);
        return result;
    }
}
