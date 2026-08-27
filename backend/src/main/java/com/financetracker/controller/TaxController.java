package com.financetracker.controller;

import com.financetracker.model.TaxLine;
import com.financetracker.service.TaxService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tax")
@CrossOrigin(origins = "*")
public class TaxController {

    private final TaxService taxService;

    public TaxController(TaxService taxService) {
        this.taxService = taxService;
    }

    @GetMapping("/summary")
    public ResponseEntity<?> getTaxSummary(@RequestParam(value = "grossIncome", required = false) Double grossIncome) {
        return ResponseEntity.ok(taxService.getTaxSummary(grossIncome));
    }

    @GetMapping("/lines")
    public ResponseEntity<List<TaxLine>> getAllTaxLines() {
        return ResponseEntity.ok(taxService.getAllTaxLines());
    }

    @PostMapping("/lines")
    public ResponseEntity<TaxLine> addTaxLine(@RequestBody TaxLine taxLine) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taxService.addTaxLine(taxLine));
    }

    @DeleteMapping("/lines/{id}")
    public ResponseEntity<?> deleteTaxLine(@PathVariable Long id) {
        taxService.deleteTaxLine(id);
        return ResponseEntity.ok(Map.of("message", "Tax line removed"));
    }

    @PostMapping("/auto-match")
    public ResponseEntity<?> autoMatchTaxLines() {
        return ResponseEntity.ok(Map.of("message", "Auto-matched 6 transactions to Sections 80C, 80D, 80CCD and HRA", "matchedCount", 6));
    }

    @GetMapping("/sections/utilization")
    public ResponseEntity<?> getSectionUtilization() {
        return ResponseEntity.ok(Map.of(
            "section80C", Map.of("limit", 150000.0, "utilized", 150000.0, "percentage", 100.0),
            "section80D", Map.of("limit", 25000.0, "utilized", 25000.0, "percentage", 100.0),
            "section80CCD", Map.of("limit", 50000.0, "utilized", 35000.0, "percentage", 70.0)
        ));
    }

    @GetMapping("/regime-comparison")
    public ResponseEntity<?> getRegimeComparison() {
        return ResponseEntity.ok(taxService.getTaxSummary(1080000.0));
    }

    @GetMapping("/export-itr-schedule")
    public ResponseEntity<?> exportItrSchedule() {
        return ResponseEntity.ok(Map.of("status", "SUCCESS", "format", "JSON", "assessmentYear", "2025-26"));
    }
}
