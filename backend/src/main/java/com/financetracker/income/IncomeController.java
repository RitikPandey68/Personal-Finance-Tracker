package com.financetracker.income;

import com.financetracker.income.model.Income;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/income")
@CrossOrigin(origins = "*")
public class IncomeController {

    @Autowired private IncomeService incomeService;

    @PostMapping
    public ResponseEntity<Income> addIncome(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody Map<String, Object> req
    ) {
        String email = userDetails != null ? userDetails.getUsername() : "ritik@example.com";
        String source = req.getOrDefault("sourceName", "Salary").toString();
        BigDecimal amount = new BigDecimal(req.getOrDefault("monthlyAmount", 50000).toString());
        String docType = req.getOrDefault("docType", "Salary Slip").toString();

        return ResponseEntity.ok(incomeService.addIncomeSource(email, source, amount, docType));
    }

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getSummary(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String email = userDetails != null ? userDetails.getUsername() : "ritik@example.com";
        return ResponseEntity.ok(incomeService.getIncomeSummary(email));
    }
}
