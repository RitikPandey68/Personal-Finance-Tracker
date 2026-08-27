package com.financetracker.service;

import com.financetracker.model.Debt;
import com.financetracker.repository.DebtRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DebtService {

    private final DebtRepository debtRepository;

    public DebtService(DebtRepository debtRepository) {
        this.debtRepository = debtRepository;
    }

    public List<Debt> getAllDebts() {
        return debtRepository.findAll();
    }

    public Debt getDebtById(Long id) {
        return debtRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Debt not found with ID: " + id));
    }

    @Transactional
    public Debt createDebt(Debt debt) {
        return debtRepository.save(debt);
    }

    @Transactional
    public Debt recordPayment(Long id, Double paymentAmount) {
        Debt debt = getDebtById(id);
        debt.setRemainingBalance(Math.max(0.0, debt.getRemainingBalance() - paymentAmount));
        return debtRepository.save(debt);
    }

    @Transactional
    public void deleteDebt(Long id) {
        debtRepository.deleteById(id);
    }

    public Map<String, Object> getDebtSummary() {
        Double totalDebt = debtRepository.sumTotalOutstandingDebtNative();
        Double totalEmi = debtRepository.sumTotalMonthlyEmiNative();

        Map<String, Object> summary = new HashMap<>();
        summary.put("totalOutstandingDebt", totalDebt);
        summary.put("totalMonthlyEmi", totalEmi);
        summary.put("debtCount", debtRepository.count());
        return summary;
    }
}
