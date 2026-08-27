package com.financetracker.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/security")
@CrossOrigin(origins = "*")
public class FinancialSecurityController {

    @Autowired private SecurityScoreService scoreService;
    @Autowired private WealthSimulatorService simulatorService;

    @GetMapping("/score")
    public ResponseEntity<Map<String, Object>> getSecurityScore() {
        return ResponseEntity.ok(scoreService.calculateSecurityScore());
    }

    @PostMapping("/wealth-forecast")
    public ResponseEntity<Map<String, Object>> forecastWealth(
            @RequestBody Map<String, Object> req
    ) {
        double income = Double.parseDouble(req.getOrDefault("monthlyIncome", 85000).toString());
        double expense = Double.parseDouble(req.getOrDefault("monthlyExpense", 55000).toString());
        double investments = Double.parseDouble(req.getOrDefault("currentInvestments", 150000).toString());
        double incomeGrowth = Double.parseDouble(req.getOrDefault("incomeGrowthPct", 8.0).toString());
        double inflation = Double.parseDouble(req.getOrDefault("expenseInflationPct", 6.0).toString());
        double returns = Double.parseDouble(req.getOrDefault("investmentReturnPct", 10.0).toString());

        return ResponseEntity.ok(simulatorService.simulateWealthForecast(
                income, expense, investments, incomeGrowth, inflation, returns, 2026, 2040));
    }
}
