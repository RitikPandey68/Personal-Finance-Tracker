package com.financetracker.recommendation;

public class RiskProfileRequest {
    private int ageGroup; // 1: <30, 2: 30-45, 3: 45-60, 4: >60
    private int investmentHorizon; // 1: <1 yr, 2: 1-3 yrs, 3: 3-5 yrs, 4: 5+ yrs
    private double monthlyIncome;
    private double monthlyExpense;
    private double debtEmi;
    private double emergencyFundMonths; // 0-12 months
    private int lossTolerance; // 1: Panic & sell, 2: Hold, 3: Buy more on dip
    private String primaryGoal; // Wealth Creation, Retirement, Tax Saving, Short-term

    public int getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(int ageGroup) {
        this.ageGroup = ageGroup;
    }

    public int getInvestmentHorizon() {
        return investmentHorizon;
    }

    public void setInvestmentHorizon(int investmentHorizon) {
        this.investmentHorizon = investmentHorizon;
    }

    public double getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(double monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public double getMonthlyExpense() {
        return monthlyExpense;
    }

    public void setMonthlyExpense(double monthlyExpense) {
        this.monthlyExpense = monthlyExpense;
    }

    public double getDebtEmi() {
        return debtEmi;
    }

    public void setDebtEmi(double debtEmi) {
        this.debtEmi = debtEmi;
    }

    public double getEmergencyFundMonths() {
        return emergencyFundMonths;
    }

    public void setEmergencyFundMonths(double emergencyFundMonths) {
        this.emergencyFundMonths = emergencyFundMonths;
    }

    public int getLossTolerance() {
        return lossTolerance;
    }

    public void setLossTolerance(int lossTolerance) {
        this.lossTolerance = lossTolerance;
    }

    public String getPrimaryGoal() {
        return primaryGoal;
    }

    public void setPrimaryGoal(String primaryGoal) {
        this.primaryGoal = primaryGoal;
    }
}
