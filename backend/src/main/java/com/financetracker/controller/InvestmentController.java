package com.financetracker.controller;

import com.financetracker.model.Investment;
import com.financetracker.service.InvestmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/investments")
@CrossOrigin(origins = "*")
public class InvestmentController {

    private final InvestmentService investmentService;

    public InvestmentController(InvestmentService investmentService) {
        this.investmentService = investmentService;
    }

    @GetMapping
    public ResponseEntity<List<Investment>> getAllHoldings() {
        return ResponseEntity.ok(investmentService.getAllHoldings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Investment> getHoldingById(@PathVariable Long id) {
        return ResponseEntity.ok(investmentService.getHoldingById(id));
    }

    @PostMapping
    public ResponseEntity<Investment> addHolding(@RequestBody Investment holding) {
        return ResponseEntity.status(HttpStatus.CREATED).body(investmentService.addHolding(holding));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Investment> updateHolding(@PathVariable Long id, @RequestBody Investment holding) {
        holding.setId(id);
        return ResponseEntity.ok(investmentService.addHolding(holding));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHolding(@PathVariable Long id) {
        investmentService.deleteHolding(id);
        return ResponseEntity.ok(Map.of("message", "Investment holding removed"));
    }

    @GetMapping("/summary")
    public ResponseEntity<?> getPortfolioSummary() {
        return ResponseEntity.ok(investmentService.getPortfolioSummary());
    }

    @GetMapping("/allocation")
    public ResponseEntity<?> getAssetAllocation() {
        return ResponseEntity.ok(investmentService.getAssetAllocation());
    }

    @GetMapping("/catalog")
    public ResponseEntity<?> getMarketCatalog() {
        return ResponseEntity.ok(investmentService.get100PlusMarketCatalog());
    }

    @GetMapping("/catalog/search")
    public ResponseEntity<?> searchCatalog(@RequestParam("q") String query) {
        return ResponseEntity.ok(investmentService.get100PlusMarketCatalog().stream()
                .filter(item -> item.get("name").toString().toLowerCase().contains(query.toLowerCase()) ||
                                item.get("ticker").toString().toLowerCase().contains(query.toLowerCase()))
                .toList());
    }

    @GetMapping("/performance/historical")
    public ResponseEntity<?> getHistoricalPerformance() {
        return ResponseEntity.ok(List.of(
            Map.of("date", "2025-08-01", "value", 120000.0),
            Map.of("date", "2025-10-01", "value", 138000.0),
            Map.of("date", "2025-12-01", "value", 151000.0),
            Map.of("date", "2026-01-01", "value", 161000.0)
        ));
    }
}
