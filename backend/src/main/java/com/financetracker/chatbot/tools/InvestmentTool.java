package com.financetracker.chatbot.tools;

import com.financetracker.model.Transaction;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.*;

@Component
public class InvestmentTool {

    public Map<String, Object> analyzeInvestments(List<Transaction> transactions) {
        BigDecimal totalInvested = transactions.stream()
                .filter(t -> "investment".equalsIgnoreCase(t.getCategory()))
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, Object> result = new HashMap<>();
        result.put("totalInvested", totalInvested);
        result.put("estimatedPortfolioValue", totalInvested.multiply(new BigDecimal("1.15"))); // 15% growth estimate
        return result;
    }
}
