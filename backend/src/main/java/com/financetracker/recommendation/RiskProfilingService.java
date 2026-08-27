package com.financetracker.recommendation;

import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class RiskProfilingService {

    public Map<String, Object> evaluateRiskProfile(RiskProfileRequest req) {
        int score = 0;

        // Age factor
        if (req.getAgeGroup() == 1) score += 25;
        else if (req.getAgeGroup() == 2) score += 20;
        else if (req.getAgeGroup() == 3) score += 12;
        else score += 5;

        // Horizon factor
        if (req.getInvestmentHorizon() == 4) score += 25;
        else if (req.getInvestmentHorizon() == 3) score += 18;
        else if (req.getInvestmentHorizon() == 2) score += 10;
        else score += 5;

        // Loss Tolerance
        if (req.getLossTolerance() == 3) score += 30;
        else if (req.getLossTolerance() == 2) score += 20;
        else score += 10;

        // Emergency fund safety boost
        if (req.getEmergencyFundMonths() >= 6) score += 20;
        else if (req.getEmergencyFundMonths() >= 3) score += 12;
        else score += 5;

        String profile = score >= 75 ? "AGGRESSIVE" : score >= 45 ? "MODERATE" : "CONSERVATIVE";

        Map<String, Integer> targetAllocation = new HashMap<>();
        if ("AGGRESSIVE".equals(profile)) {
            targetAllocation.put("Equity", 70);
            targetAllocation.put("Debt", 15);
            targetAllocation.put("Gold", 10);
            targetAllocation.put("Cash", 5);
        } else if ("MODERATE".equals(profile)) {
            targetAllocation.put("Equity", 55);
            targetAllocation.put("Debt", 25);
            targetAllocation.put("Gold", 10);
            targetAllocation.put("Cash", 10);
        } else {
            targetAllocation.put("Equity", 30);
            targetAllocation.put("Debt", 50);
            targetAllocation.put("Gold", 10);
            targetAllocation.put("Cash", 10);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("score", score);
        result.put("riskProfile", profile);
        result.put("targetAllocation", targetAllocation);
        result.put("description", String.format("Based on your assessment, your Risk Profile is %s.", profile));
        return result;
    }
}
