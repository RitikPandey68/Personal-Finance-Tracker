package com.financetracker.recovery;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecoveryScenario {
    private String scenarioName;
    private double expectedReturnRate;
    private BigDecimal monthlyInvestment;
    private double recoveryYears;
    private int recoveryMonths;
    private List<Map<String, Object>> projectionPoints; // [{year: 1, value: 92000}, ...]
}
