package com.financetracker.controller;

import com.financetracker.model.Transaction;
import com.financetracker.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "*")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public ResponseEntity<List<Transaction>> getAll(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        return ResponseEntity.ok(
            transactionService.getTransactions(userDetails.getUsername(), type, category, startDate, endDate)
        );
    }

    @PostMapping
    public ResponseEntity<Transaction> create(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody Transaction transaction
    ) {
        return ResponseEntity.ok(transactionService.createTransaction(userDetails.getUsername(), transaction));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transaction> update(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id,
            @Valid @RequestBody Transaction transaction
    ) {
        return ResponseEntity.ok(transactionService.updateTransaction(userDetails.getUsername(), id, transaction));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id
    ) {
        transactionService.deleteTransaction(userDetails.getUsername(), id);
        return ResponseEntity.ok(Map.of("message", "Transaction deleted successfully"));
    }

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getSummary(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(defaultValue = "MONTH") String period
    ) {
        return ResponseEntity.ok(transactionService.getSummary(userDetails.getUsername(), period));
    }

    @GetMapping("/export/csv")
    public ResponseEntity<byte[]> exportCsv(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        byte[] csv = transactionService.exportToCsv(userDetails.getUsername());
        return ResponseEntity.ok()
            .header("Content-Type", "text/csv")
            .header("Content-Disposition", "attachment; filename=transactions.csv")
            .body(csv);
    }

    @GetMapping("/health-score")
    public ResponseEntity<Map<String, Object>> getFinancialHealthScore(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return ResponseEntity.ok(transactionService.calculateFinancialHealthScore(userDetails.getUsername()));
    }
}
