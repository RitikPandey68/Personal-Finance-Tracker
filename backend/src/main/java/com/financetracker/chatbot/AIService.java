package com.financetracker.chatbot;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Map;

@Service
public class AIService {

    public String generateResponse(String userPrompt, Map<String, Object> context) {
        String query = userPrompt.toLowerCase().trim();

        BigDecimal income = (BigDecimal) context.getOrDefault("totalIncome", BigDecimal.ZERO);
        BigDecimal expenses = (BigDecimal) context.getOrDefault("totalExpenses", BigDecimal.ZERO);
        BigDecimal savings = (BigDecimal) context.getOrDefault("netSavings", BigDecimal.ZERO);
        Object savingsRate = context.getOrDefault("savingsRate", 0);

        @SuppressWarnings("unchecked")
        Map<String, Object> expAnalysis = (Map<String, Object>) context.get("expenseAnalysis");
        String topCategory = expAnalysis != null ? (String) expAnalysis.getOrDefault("highestCategory", "general") : "general";
        BigDecimal topCatAmount = expAnalysis != null ? (BigDecimal) expAnalysis.getOrDefault("highestCategoryAmount", BigDecimal.ZERO) : BigDecimal.ZERO;

        @SuppressWarnings("unchecked")
        Map<String, Object> health = (Map<String, Object>) context.get("healthScore");
        int score = health != null ? (Integer) health.getOrDefault("score", 70) : 70;
        String grade = health != null ? (String) health.getOrDefault("grade", "Good") : "Good";

        // 1. Spending & Expenses
        if (query.contains("spending") || query.contains("expense") || query.contains("kharcha") || query.contains("kaisa hai")) {
            return String.format("📊 **Monthly Spending Overview**:\n\n" +
                            "• Total Income: ₹%,.2f\n" +
                            "• Total Expenses: ₹%,.2f\n" +
                            "• Net Savings: ₹%,.2f (%s%% savings rate)\n\n" +
                            "🔥 Your highest spending category is **%s** with a total of ₹%,.2f. " +
                            (expenses.compareTo(income.multiply(new BigDecimal("0.7"))) > 0
                                    ? "⚠️ You are spending more than 70% of your income. Consider reducing discretionary costs!"
                                    : "✅ Your spending is well within healthy limits! 🎉"),
                    income, expenses, savings, savingsRate, topCategory, topCatAmount);
        }

        // 2. Highest Spending / Where spending most
        if (query.contains("where") || query.contains("highest") || query.contains("most") || query.contains("kyun") || query.contains("why high")) {
            return String.format("🔍 **Highest Expenditure Breakdown**:\n\n" +
                            "Your top expense driver is **%s**, accounting for ₹%,.2f this month. " +
                            "To lower overall expenses, evaluate subscriptions, dining out, or impulse shopping under this category.",
                    topCategory, topCatAmount);
        }

        // 3. Affordability ("Can I afford X?")
        if (query.contains("afford") || query.contains("buy") || query.contains("kharid")) {
            return String.format("💳 **Affordability Analysis**:\n\n" +
                            "Your current net monthly savings stand at ₹%,.2f. " +
                            "If the planned purchase is less than your monthly savings or emergency fund buffer, it is affordable! " +
                            "Otherwise, consider creating a dedicated target Goal in FinanceFlow.",
                    savings);
        }

        // 4. Budget Questions
        if (query.contains("budget") || query.contains("limit")) {
            BigDecimal remaining = income.subtract(expenses);
            return String.format("🎯 **Budget Status**:\n\n" +
                            "• Total Monthly Budget Pool: ₹%,.2f\n" +
                            "• Total Spent So Far: ₹%,.2f\n" +
                            "• Remaining Budget: ₹%,.2f\n\n" +
                            (remaining.compareTo(BigDecimal.ZERO) < 0
                                    ? "⚠️ You have exceeded your overall budget limit!"
                                    : "✅ You have ₹%,.2f left to spend safely this month."),
                    income, expenses, remaining, remaining);
        }

        // 5. Investment Portfolio Questions
        if (query.contains("invest") || query.contains("portfolio") || query.contains("stock") || query.contains("mutual")) {
            @SuppressWarnings("unchecked")
            Map<String, Object> inv = (Map<String, Object>) context.get("investmentAnalysis");
            BigDecimal totalInv = inv != null ? (BigDecimal) inv.getOrDefault("totalInvested", BigDecimal.ZERO) : BigDecimal.ZERO;
            BigDecimal estVal = inv != null ? (BigDecimal) inv.getOrDefault("estimatedPortfolioValue", BigDecimal.ZERO) : BigDecimal.ZERO;

            return String.format("📈 **Investment Summary**:\n\n" +
                            "• Total Invested: ₹%,.2f\n" +
                            "• Estimated Portfolio Growth: ₹%,.2f\n\n" +
                            "💡 Reinvesting at least 20%% of net savings monthly will accelerate your long-term wealth compounding!",
                    totalInv, estVal);
        }

        // 6. Financial Health Score
        if (query.contains("health") || query.contains("score") || query.contains("status")) {
            return String.format("🏥 **Financial Health Score**: %d/100 (%s)\n\n" +
                            "• Savings Rate: %s%%\n" +
                            "• Monthly Income: ₹%,.2f\n" +
                            "• Monthly Expense: ₹%,.2f\n\n" +
                            "💡 Tip: Boost your health score to 80+ by maintaining a 30%%+ savings rate and building a 6-month emergency fund.",
                    score, grade, savingsRate, income, expenses);
        }

        // 7. General Summary / Advice Default
        return String.format("🤖 **FinanceFlow AI Summary**:\n\n" +
                        "Here is your current financial pulse:\n" +
                        "• Monthly Income: ₹%,.2f\n" +
                        "• Monthly Expenses: ₹%,.2f\n" +
                        "• Net Savings: ₹%,.2f\n" +
                        "• Top Spend Category: %s (₹%,.2f)\n\n" +
                        "Ask me anything like:\n" +
                        "1. *\"Mera is month spending kaisa hai?\"*\n" +
                        "2. *\"Where am I spending most?\"*\n" +
                        "3. *\"Can I afford a 20k gadget?\"*\n" +
                        "4. *\"How is my financial health score?\"*",
                income, expenses, savings, topCategory, topCatAmount);
    }
}
