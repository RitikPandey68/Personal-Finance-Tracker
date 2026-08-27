package com.financetracker.recovery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.*;

@Service
public class RecoveryService {

    @Autowired private RecoveryCalculator calculator;

    public Map<String, Object> simulateRecovery(BigDecimal originalValue, BigDecimal currentValue,
                                                BigDecimal monthlyInvestment) {
        Map<String, Object> metrics = calculator.calculateLossMetrics(originalValue, currentValue);

        RecoveryScenario scenarioA = calculator.generateScenario(
                "Scenario A (Conservative 8%)", currentValue, originalValue, monthlyInvestment, 8.0, 5);

        RecoveryScenario scenarioB = calculator.generateScenario(
                "Scenario B (Moderate 12%)", currentValue, originalValue, monthlyInvestment, 12.0, 5);

        RecoveryScenario scenarioC = calculator.generateScenario(
                "Scenario C (Aggressive 15%)", currentValue, originalValue, monthlyInvestment, 15.0, 5);

        Map<String, Object> result = new HashMap<>(metrics);
        result.put("monthlyInvestment", monthlyInvestment);
        result.put("scenarios", List.of(scenarioA, scenarioB, scenarioC));
        result.put("disclaimer", "Recovery projections are calculated estimates based on compound return assumptions and do not guarantee market performance.");
        return result;
    }
}
