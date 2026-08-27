package com.financetracker.chatbot.tools;

import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.*;

@Component
public class BudgetTool {

    public Map<String, Object> evaluateBudgetStatus(BigDecimal totalIncome, BigDecimal totalExpenses) {
        BigDecimal remaining = totalIncome.subtract(totalExpenses);
        double usagePercent = totalIncome.compareTo(BigDecimal.ZERO) > 0
                ? totalExpenses.divide(totalIncome, 4, java.math.RoundingMode.HALF_UP).doubleValue() * 100
                : 0.0;

        Map<String, Object> result = new HashMap<>();
        result.put("totalIncome", totalIncome);
        result.put("totalExpenses", totalExpenses);
        result.put("remainingBudget", remaining);
        result.put("usagePercent", usagePercent);
        result.put("status", usagePercent >= 100 ? "OVER_BUDGET" : usagePercent >= 80 ? "NEAR_LIMIT" : "HEALTHY");
        return result;
    }
}
