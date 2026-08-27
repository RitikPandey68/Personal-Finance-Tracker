package com.financetracker.income;

import com.financetracker.income.model.Income;
import com.financetracker.income.repository.IncomeRepository;
import com.financetracker.model.User;
import com.financetracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.*;

@Service
public class IncomeService {

    @Autowired private IncomeRepository incomeRepo;
    @Autowired private UserRepository userRepo;

    public Income addIncomeSource(String userEmail, String sourceName, BigDecimal amount, String docType) {
        User user = userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found: " + userEmail));
        Income income = new Income(user, sourceName, amount, docType);
        return incomeRepo.save(income);
    }

    public List<Income> getUserIncomes(String userEmail) {
        User user = userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found: " + userEmail));
        return incomeRepo.findByUser(user);
    }

    public Map<String, Object> getIncomeSummary(String userEmail) {
        List<Income> incomes = getUserIncomes(userEmail);
        BigDecimal total = incomes.stream()
                .map(Income::getMonthlyAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, Object> summary = new HashMap<>();
        summary.put("incomes", incomes);
        summary.put("totalMonthlyIncome", total);
        return summary;
    }
}
