package com.financetracker.recovery;

import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Component
public class RecoveryCalculator {

    public Map<String, Object> calculateLossMetrics(BigDecimal originalValue, BigDecimal currentValue) {
        BigDecimal lossAmount = originalValue.subtract(currentValue);
        double lossPercentage = originalValue.compareTo(BigDecimal.ZERO) > 0
                ? lossAmount.divide(originalValue, 4, RoundingMode.HALF_UP).doubleValue() * 100
                : 0.0;

        double recoveryRequiredPct = currentValue.compareTo(BigDecimal.ZERO) > 0
                ? lossAmount.divide(currentValue, 4, RoundingMode.HALF_UP).doubleValue() * 100
                : 0.0;

        Map<String, Object> metrics = new HashMap<>();
        metrics.put("originalValue", originalValue);
        metrics.put("currentValue", currentValue);
        metrics.put("lossAmount", lossAmount);
        metrics.put("lossPercentage", Math.max(0, lossPercentage));
        metrics.put("recoveryRequiredPct", Math.max(0, recoveryRequiredPct));
        return metrics;
    }

    public RecoveryScenario generateScenario(String name, BigDecimal currentValue, BigDecimal targetValue,
                                             BigDecimal monthlyInv, double annualReturnPct, int projectionYears) {
        double r = annualReturnPct / 100.0 / 12.0; // monthly return rate
        double current = currentValue.doubleValue();
        double target = targetValue.doubleValue();
        double pmt = monthlyInv.doubleValue();

        List<Map<String, Object>> projection = new ArrayList<>();
        double runningVal = current;
        int monthsToRecover = -1;

        for (int m = 1; m <= projectionYears * 12; m++) {
            runningVal = runningVal * (1 + r) + pmt;

            if (runningVal >= target && monthsToRecover == -1) {
                monthsToRecover = m;
            }

            if (m % 12 == 0) {
                Map<String, Object> pt = new HashMap<>();
                pt.put("year", m / 12);
                pt.put("value", Math.round(runningVal));
                projection.add(pt);
            }
        }

        double recoveryYears = monthsToRecover > 0 ? (double) monthsToRecover / 12.0 : projectionYears;

        return new RecoveryScenario(
                name,
                annualReturnPct,
                monthlyInv,
                Math.round(recoveryYears * 10.0) / 10.0,
                monthsToRecover > 0 ? monthsToRecover : projectionYears * 12,
                projection
        );
    }
}
