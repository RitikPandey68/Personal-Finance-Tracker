package com.financetracker.recommendation;

import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class PortfolioAnalysisService {

    public Map<String, Object> analyzeCurrentPortfolio() {
        // Current sample allocation breakdown
        Map<String, Integer> currentAllocation = new HashMap<>();
        currentAllocation.put("Equity", 72);
        currentAllocation.put("Debt", 10);
        currentAllocation.put("Gold", 8);
        currentAllocation.put("Cash", 10);

        List<String> findings = new ArrayList<>();
        findings.add("Your portfolio currently has a high equity concentration (72%).");
        findings.add("Debt allocation (10%) is lower than recommended for risk mitigation.");

        Map<String, Object> analysis = new HashMap<>();
        analysis.put("currentAllocation", currentAllocation);
        analysis.put("findings", findings);
        return analysis;
    }
}
