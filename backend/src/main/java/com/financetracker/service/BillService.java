package com.financetracker.service;

import com.financetracker.model.Bill;
import com.financetracker.repository.BillRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BillService {

    private final BillRepository billRepository;

    public BillService(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    public List<Bill> getUpcomingUnpaidBills() {
        return billRepository.findByPaidFalse();
    }

    @Transactional
    public Bill createBill(Bill bill) {
        return billRepository.save(bill);
    }

    @Transactional
    public Bill markBillPaid(Long id) {
        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Bill not found with ID: " + id));
        bill.setPaid(true);
        return billRepository.save(bill);
    }

    @Transactional
    public void deleteBill(Long id) {
        billRepository.deleteById(id);
    }

    public Map<String, Object> getBillsSummary() {
        Double unpaidTotal = billRepository.sumUpcomingUnpaidBillsNative();
        List<Bill> unpaidBills = billRepository.findByPaidFalse();

        Map<String, Object> summary = new HashMap<>();
        summary.put("unpaidBillsCount", unpaidBills.size());
        summary.put("totalUnpaidAmount", unpaidTotal);
        return summary;
    }
}
