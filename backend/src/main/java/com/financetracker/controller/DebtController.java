package com.financetracker.controller;

import com.financetracker.model.Debt;
import com.financetracker.service.DebtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/debts")
@CrossOrigin(origins = "*")
public class DebtController {

    private final DebtService debtService;

    public DebtController(DebtService debtService) {
        this.debtService = debtService;
    }

    @GetMapping
    public ResponseEntity<List<Debt>> getAllDebts() {
        return ResponseEntity.ok(debtService.getAllDebts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Debt> getDebtById(@PathVariable Long id) {
        return ResponseEntity.ok(debtService.getDebtById(id));
    }

    @PostMapping
    public ResponseEntity<Debt> createDebt(@RequestBody Debt debt) {
        return ResponseEntity.status(HttpStatus.CREATED).body(debtService.createDebt(debt));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Debt> updateDebt(@PathVariable Long id, @RequestBody Debt debt) {
        debt.setId(id);
        return ResponseEntity.ok(debtService.createDebt(debt));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDebt(@PathVariable Long id) {
        debtService.deleteDebt(id);
        return ResponseEntity.ok(Map.of("message", "Debt settled or removed"));
    }

    @PostMapping("/{id}/payment")
    public ResponseEntity<Debt> recordPayment(@PathVariable Long id, @RequestBody Map<String, Double> body) {
        Double amount = body.getOrDefault("amount", 0.0);
        return ResponseEntity.ok(debtService.recordPayment(id, amount));
    }

    @GetMapping("/strategies/snowball")
    public ResponseEntity<?> getDebtSnowballPlan() {
        return ResponseEntity.ok(Map.of(
            "strategy", "Debt Snowball (Lowest Balance First)",
            "monthsToDebtFree", 18.5,
            "totalInterestSaved", 42000.0,
            "recommendedOrder", List.of("SBI Education Loan", "HDFC Car Loan")
        ));
    }

    @GetMapping("/strategies/avalanche")
    public ResponseEntity<?> getDebtAvalanchePlan() {
        return ResponseEntity.ok(Map.of(
            "strategy", "Debt Avalanche (Highest Interest First)",
            "monthsToDebtFree", 17.2,
            "totalInterestSaved", 58500.0,
            "recommendedOrder", List.of("HDFC Car Loan (8.5%)", "SBI Education Loan (7.2%)")
        ));
    }
}
