package com.financetracker.recommendation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class RecommendationService {

    @Autowired private RiskProfilingService riskService;
    @Autowired private PortfolioAnalysisService portfolioService;

    public Map<String, Object> generateRecommendations(RiskProfileRequest req) {
        Map<String, Object> riskResult = riskService.evaluateRiskProfile(req);
        Map<String, Object> portfolioAnalysis = portfolioService.analyzeCurrentPortfolio();

        @SuppressWarnings("unchecked")
        Map<String, Integer> target = (Map<String, Integer>) riskResult.get("targetAllocation");

        List<String> recommendations = new ArrayList<>();
        recommendations.add("Strategic Asset Allocation: Target Equity " + target.get("Equity") + "%, Debt " + target.get("Debt") + "%, Gold " + target.get("Gold") + "%.");
        recommendations.add("Rebalance Portfolio: Consider directing fresh monthly SIPs towards High-Quality Corporate Debt/Bonds to reach the " + target.get("Debt") + "% target.");
        recommendations.add("Emergency Buffer: Ensure 6 months of expenses are kept liquid in High-Yield Savings or Liquid Funds.");

        Map<String, Object> response = new HashMap<>();
        response.put("riskEvaluation", riskResult);
        response.put("portfolioAnalysis", portfolioAnalysis);
        response.put("recommendations", recommendations);
        return response;
    }
}
