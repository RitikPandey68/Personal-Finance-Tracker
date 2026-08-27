package com.financetracker.controller;

import com.financetracker.model.Bill;
import com.financetracker.service.BillService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bills")
@CrossOrigin(origins = "*")
public class BillController {

    private final BillService billService;

    public BillController(BillService billService) {
        this.billService = billService;
    }

    @GetMapping
    public ResponseEntity<List<Bill>> getAllBills() {
        return ResponseEntity.ok(billService.getAllBills());
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<Bill>> getUpcomingBills() {
        return ResponseEntity.ok(billService.getUpcomingUnpaidBills());
    }

    @PostMapping
    public ResponseEntity<Bill> createBill(@RequestBody Bill bill) {
        return ResponseEntity.status(HttpStatus.CREATED).body(billService.createBill(bill));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bill> updateBill(@PathVariable Long id, @RequestBody Bill bill) {
        bill.setId(id);
        return ResponseEntity.ok(billService.createBill(bill));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBill(@PathVariable Long id) {
        billService.deleteBill(id);
        return ResponseEntity.ok(Map.of("message", "Bill deleted successfully"));
    }

    @PostMapping("/{id}/pay")
    public ResponseEntity<Bill> markBillPaid(@PathVariable Long id) {
        return ResponseEntity.ok(billService.markBillPaid(id));
    }

    @GetMapping("/overdue")
    public ResponseEntity<?> getOverdueBills() {
        return ResponseEntity.ok(List.of());
    }

    @GetMapping("/calendar")
    public ResponseEntity<?> getBillsCalendar() {
        return ResponseEntity.ok(billService.getBillsSummary());
    }
}
