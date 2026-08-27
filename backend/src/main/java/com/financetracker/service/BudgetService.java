package com.financetracker.service;

import com.financetracker.model.Budget;
import com.financetracker.repository.BudgetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BudgetService {

    private final BudgetRepository budgetRepository;

    public BudgetService(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    public List<Budget> getAllBudgets() {
        return budgetRepository.findAll();
    }

    public Budget getBudgetById(Long id) {
        return budgetRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Budget not found with ID: " + id));
    }

    @Transactional
    public Budget createOrUpdateBudget(Budget budget) {
        return budgetRepository.save(budget);
    }

    @Transactional
    public void deleteBudget(Long id) {
        budgetRepository.deleteById(id);
    }

    public Map<String, Object> getBudgetSummary() {
        Double totalBudgeted = budgetRepository.sumTotalBudgetedNative();
        Double totalSpent = budgetRepository.sumTotalSpentNative();
        Double remaining = totalBudgeted - totalSpent;
        Double percentageSpent = totalBudgeted > 0 ? (totalSpent / totalBudgeted) * 100 : 0.0;

        Map<String, Object> summary = new HashMap<>();
        summary.put("totalBudgeted", totalBudgeted);
        summary.put("totalSpent", totalSpent);
        summary.put("remaining", remaining);
        summary.put("percentageSpent", Math.round(percentageSpent * 10.0) / 10.0);
        summary.put("isOverBudget", totalSpent > totalBudgeted);
        return summary;
    }
}
