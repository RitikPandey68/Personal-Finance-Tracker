package com.financetracker.controller;

import com.financetracker.service.AiFinancialService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "*")
public class AiIntelligenceController {

    private final AiFinancialService aiFinancialService;

    public AiIntelligenceController(AiFinancialService aiFinancialService) {
        this.aiFinancialService = aiFinancialService;
    }

    @PostMapping("/chat")
    public ResponseEntity<?> chat(@RequestBody Map<String, String> body) {
        String query = body.getOrDefault("query", "");
        return ResponseEntity.ok(aiFinancialService.processFinancialChat(query));
    }

    @GetMapping("/health-score")
    public ResponseEntity<?> getHealthScore() {
        return ResponseEntity.ok(aiFinancialService.computeHealthScore());
    }

    @GetMapping("/insights")
    public ResponseEntity<?> getInsights() {
        return ResponseEntity.ok(List.of(
            Map.of("icon", "fa-piggy-bank", "color", "green", "text", "Your monthly savings rate of 58.8% puts you well above the 50-30-20 benchmark."),
            Map.of("icon", "fa-chart-line", "color", "blue", "text", "Diversifying into GoldBeES & SGBs has hedged against recent market volatility."),
            Map.of("icon", "fa-file-invoice", "color", "purple", "text", "Section 80C limit (₹1.5 Lakhs) is 100% maxed out. Invest in NPS for extra ₹50k tax savings under 80CCD(1B).")
        ));
    }

    @PostMapping("/recovery/simulate")
    public ResponseEntity<?> simulateRecovery(@RequestBody Map<String, Double> body) {
        Double invested = body.get("investedAmount");
        Double current = body.get("currentValue");
        Double sip = body.get("monthlySip");
        Double cagr = body.get("expectedCagr");
        return ResponseEntity.ok(aiFinancialService.simulateLossRecovery(invested, current, sip, cagr));
    }

    @PostMapping("/recommendation/risk-profile")
    public ResponseEntity<?> assessRiskProfile(@RequestBody Map<String, Object> answers) {
        return ResponseEntity.ok(Map.of(
            "riskProfile", "Moderate Aggressive",
            "score", 74,
            "recommendedAllocation", Map.of("Equity", "65%", "Debt & Gold", "25%", "Liquid Buffer", "10%")
        ));
    }

    @GetMapping("/security-forecast")
    public ResponseEntity<?> getSecurityForecast() {
        return ResponseEntity.ok(Map.of(
            "futureSecurityIndex", 82,
            "emergencyFundAdequacy", "4.8 Months",
            "cashRunway", "14.2 Months",
            "insuranceCoverageRating", "Optimum (Health + Term LIC Active)"
        ));
    }
}
