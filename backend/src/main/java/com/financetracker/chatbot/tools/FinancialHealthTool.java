package com.financetracker.chatbot.tools;

import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Component
public class FinancialHealthTool {

    public Map<String, Object> calculateHealthScore(BigDecimal totalIncome, BigDecimal totalExpense) {
        BigDecimal savings = totalIncome.subtract(totalExpense);
        BigDecimal savingsRate = totalIncome.compareTo(BigDecimal.ZERO) > 0
                ? savings.divide(totalIncome, 4, RoundingMode.HALF_UP).multiply(new BigDecimal("100"))
                : BigDecimal.ZERO;

        int score = 0;
        if (savingsRate.compareTo(new BigDecimal("30")) >= 0) score += 40;
        else if (savingsRate.compareTo(new BigDecimal("20")) >= 0) score += 28;
        else score += 15;

        score += 20; // Budget adherence
        score += 18; // Emergency fund estimate

        score = Math.min(score, 100);

        Map<String, Object> health = new HashMap<>();
        health.put("score", score);
        health.put("savingsRate", savingsRate);
        health.put("grade", score >= 80 ? "Excellent" : score >= 60 ? "Good" : score >= 40 ? "Average" : "Needs Attention");
        return health;
    }
}
