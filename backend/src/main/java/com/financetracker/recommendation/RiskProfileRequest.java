package com.financetracker.recommendation;

import lombok.Data;

@Data
public class RiskProfileRequest {
    private int ageGroup; // 1: <30, 2: 30-45, 3: 45-60, 4: >60
    private int investmentHorizon; // 1: <1 yr, 2: 1-3 yrs, 3: 3-5 yrs, 4: 5+ yrs
    private double monthlyIncome;
    private double monthlyExpense;
    private double debtEmi;
    private double emergencyFundMonths; // 0-12 months
    private int lossTolerance; // 1: Panic & sell, 2: Hold, 3: Buy more on dip
    private String primaryGoal; // Wealth Creation, Retirement, Tax Saving, Short-term
}
