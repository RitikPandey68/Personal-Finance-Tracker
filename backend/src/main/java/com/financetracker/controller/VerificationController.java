package com.financetracker.controller;

import com.financetracker.model.IncomeSource;
import com.financetracker.service.VerificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/verification")
@CrossOrigin(origins = "*")
public class VerificationController {

    private final VerificationService verificationService;

    public VerificationController(VerificationService verificationService) {
        this.verificationService = verificationService;
    }

    @PostMapping("/pan/verify")
    public ResponseEntity<?> verifyPan(@RequestBody Map<String, String> body) {
        String pan = body.get("pan");
        String name = body.get("fullName");
        return ResponseEntity.ok(verificationService.verifyPanCard(pan, name));
    }

    @GetMapping("/pan/status")
    public ResponseEntity<?> getPanStatus() {
        return ResponseEntity.ok(Map.of("pan", "ABCPP1234F", "verified", true, "status", "Verified (ITD/NSDL Validated)"));
    }

    @GetMapping("/income-sources")
    public ResponseEntity<List<IncomeSource>> getAllIncomeSources() {
        return ResponseEntity.ok(verificationService.getAllIncomeSources());
    }

    @PostMapping("/income-sources")
    public ResponseEntity<IncomeSource> addIncomeSource(@RequestBody IncomeSource source) {
        return ResponseEntity.status(HttpStatus.CREATED).body(verificationService.addIncomeSource(source));
    }

    @DeleteMapping("/income-sources/{id}")
    public ResponseEntity<?> deleteIncomeSource(@PathVariable Long id) {
        verificationService.deleteIncomeSource(id);
        return ResponseEntity.ok(Map.of("message", "Income source removed"));
    }

    @GetMapping("/income-sources/total")
    public ResponseEntity<?> getIncomeTotal() {
        return ResponseEntity.ok(verificationService.getIncomeSummary());
    }
}
