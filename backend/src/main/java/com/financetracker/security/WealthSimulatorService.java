package com.financetracker.security;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.*;

@Service
public class WealthSimulatorService {

    public Map<String, Object> simulateWealthForecast(
            double currentIncome, double currentExpense, double currentInvestments,
            double incomeGrowthPct, double expenseInflationPct, double investmentReturnPct, int startYear, int targetYear) {

        List<Map<String, Object>> yearlyProjections = new ArrayList<>();

        double annualIncome = currentIncome * 12;
        double annualExpense = currentExpense * 12;
        double portfolio = currentInvestments;

        for (int yr = startYear; yr <= targetYear; yr++) {
            double annualSavings = Math.max(0, annualIncome - annualExpense);
            portfolio = (portfolio + annualSavings) * (1 + (investmentReturnPct / 100.0));

            if (yr == 2026 || yr == 2030 || yr == 2035 || yr == 2040 || yr == startYear || yr == targetYear) {
                Map<String, Object> point = new HashMap<>();
                point.put("year", yr);
                point.put("portfolioValue", Math.round(portfolio));
                point.put("annualIncome", Math.round(annualIncome));
                point.put("annualExpense", Math.round(annualExpense));
                yearlyProjections.add(point);
            }

            annualIncome *= (1 + (incomeGrowthPct / 100.0));
            annualExpense *= (1 + (expenseInflationPct / 100.0));
        }

        Map<String, Object> result = new HashMap<>();
        result.put("startYear", startYear);
        result.put("targetYear", targetYear);
        result.put("assumptions", Map.of(
                "incomeGrowthPct", incomeGrowthPct,
                "expenseInflationPct", expenseInflationPct,
                "investmentReturnPct", investmentReturnPct
        ));
        result.put("projections", yearlyProjections);
        return result;
    }
}
