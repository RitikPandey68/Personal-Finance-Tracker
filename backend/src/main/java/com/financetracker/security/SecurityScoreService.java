package com.financetracker.security;

import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class SecurityScoreService {

    public Map<String, Object> calculateSecurityScore() {
        Map<String, Integer> breakdown = new HashMap<>();
        breakdown.put("Emergency Fund", 85);
        breakdown.put("Debt Protection", 72);
        breakdown.put("Goal Readiness", 80);
        breakdown.put("Retirement Readiness", 65);
        breakdown.put("Diversification", 75);

        int totalScore = (int) breakdown.values().stream().mapToInt(Integer::intValue).average().orElse(75);

        List<String> keyStrengths = List.of(
                "Strong emergency fund covering 6+ months of living expenses.",
                "Healthy savings allocation towards medium-term financial goals."
        );

        List<String> improvementAreas = List.of(
                "Boost retirement corpus contributions by 5% to improve retirement preparedness.",
                "Opt for term life and health insurance coverage to enhance debt protection."
        );

        Map<String, Object> result = new HashMap<>();
        result.put("overallScore", totalScore);
        result.put("breakdown", breakdown);
        result.put("strengths", keyStrengths);
        result.put("improvements", improvementAreas);
        return result;
    }
}
