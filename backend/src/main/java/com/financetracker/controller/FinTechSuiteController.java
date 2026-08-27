package com.financetracker.controller;

import com.financetracker.service.FinTechSuiteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/fintech")
@CrossOrigin(origins = "*")
public class FinTechSuiteController {

    private final FinTechSuiteService finTechSuiteService;

    public FinTechSuiteController(FinTechSuiteService finTechSuiteService) {
        this.finTechSuiteService = finTechSuiteService;
    }

    // 1. PDF Statement & Invoice Parser
    @PostMapping("/pdf-parser/extract")
    public ResponseEntity<?> extractPdf(@RequestBody(required = false) Map<String, String> body) {
        String filename = (body != null) ? body.get("filename") : "HDFC_Statement.pdf";
        return ResponseEntity.ok(finTechSuiteService.parseBankStatementPdf(filename));
    }

    // 2. UPI Duplicate & Ghost Subscriptions Anomaly Detector
    @GetMapping("/anomaly/scan")
    public ResponseEntity<?> scanAnomalies() {
        return ResponseEntity.ok(finTechSuiteService.scanUpiAnomalies());
    }

    // 3. Double-Entry General Ledger (T-Accounts)
    @GetMapping("/ledger/t-accounts")
    public ResponseEntity<?> getTAccounts() {
        return ResponseEntity.ok(finTechSuiteService.getGeneralLedgerTAccounts());
    }

    @PostMapping("/ledger/post-entry")
    public ResponseEntity<?> postJournalEntry(@RequestBody Map<String, Object> body) {
        String debit = (String) body.get("debitAccount");
        String credit = (String) body.get("creditAccount");
        Double amount = Double.parseDouble(body.get("amount").toString());
        String narration = (String) body.get("narration");
        return ResponseEntity.ok(finTechSuiteService.postJournalEntry(debit, credit, amount, narration));
    }

    // 4. Multi-Currency & Cross-Border FX Loss Tracker
    @GetMapping("/fx/spread-analysis")
    public ResponseEntity<?> analyzeFx(@RequestParam(value = "amount", defaultValue = "2500") Double amount,
                                       @RequestParam(value = "currency", defaultValue = "USD") String currency) {
        return ResponseEntity.ok(finTechSuiteService.analyzeFxLoss(amount, currency));
    }

    // 5. WhatsApp & Telegram Webhook Simulator
    @PostMapping("/webhook/chat-expense")
    public ResponseEntity<?> processWebhookExpense(@RequestBody Map<String, String> body) {
        String message = body.getOrDefault("message", "Spent 350 on Uber cab");
        return ResponseEntity.ok(finTechSuiteService.processChatExpenseWebhook(message));
    }

    // 6. Tax-Loss Harvesting Opportunities
    @GetMapping("/tax-harvesting/opportunities")
    public ResponseEntity<?> getTaxLossOpportunities() {
        return ResponseEntity.ok(finTechSuiteService.getTaxLossHarvestingOpportunities());
    }

    // 7. Smart Credit Card Switcher
    @PostMapping("/card-switcher/recommend")
    public ResponseEntity<?> recommendCard(@RequestBody Map<String, Object> body) {
        String merchant = (String) body.getOrDefault("merchant", "Swiggy");
        Double amount = body.containsKey("amount") ? Double.parseDouble(body.get("amount").toString()) : 1200.0;
        return ResponseEntity.ok(finTechSuiteService.recommendCreditCard(merchant, amount));
    }

    // 8. Accounts Receivable (AR) Aging & Invoice Chaser
    @GetMapping("/invoices/aging-report")
    public ResponseEntity<?> getAgingReport() {
        return ResponseEntity.ok(finTechSuiteService.getAccountsReceivableAging());
    }

    // 9. Inflation-Adjusted Goal Simulator
    @PostMapping("/goals/inflation-sim")
    public ResponseEntity<?> simulateInflationGoal(@RequestBody Map<String, Object> body) {
        String title = (String) body.getOrDefault("goalTitle", "Child Education");
        Double cost = Double.parseDouble(body.getOrDefault("todayCost", 3000000.0).toString());
        int years = Integer.parseInt(body.getOrDefault("years", 12).toString());
        Double inflation = Double.parseDouble(body.getOrDefault("inflation", 8.0).toString());
        return ResponseEntity.ok(finTechSuiteService.simulateInflationGoal(title, cost, years, inflation));
    }

    // 10. Vendor GST 2B vs 3B ITC Reconciler
    @GetMapping("/gst/reconcile-itc")
    public ResponseEntity<?> reconcileGstItc() {
        return ResponseEntity.ok(finTechSuiteService.reconcileGstItc());
    }

    // 11. Splitwise Group Expense & Dynamic UPI QR Generator
    @PostMapping("/splitwise/settle")
    public ResponseEntity<?> splitwiseSettle(@RequestBody Map<String, Object> body) {
        String trip = (String) body.getOrDefault("tripName", "Goa Trip");
        Double total = Double.parseDouble(body.getOrDefault("totalExpense", 16000.0).toString());
        List<String> members = (List<String>) body.get("members");
        String payer = (String) body.getOrDefault("paidBy", "Ritik Pandey");
        return ResponseEntity.ok(finTechSuiteService.splitGroupExpense(trip, total, members, payer));
    }
}
