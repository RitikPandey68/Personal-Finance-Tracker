package com.financetracker.service;

import com.financetracker.model.TaxLine;
import com.financetracker.repository.TaxLineRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TaxService {

    private final TaxLineRepository taxLineRepository;

    public TaxService(TaxLineRepository taxLineRepository) {
        this.taxLineRepository = taxLineRepository;
    }

    public List<TaxLine> getAllTaxLines() {
        return taxLineRepository.findAll();
    }

    @Transactional
    public TaxLine addTaxLine(TaxLine taxLine) {
        return taxLineRepository.save(taxLine);
    }

    @Transactional
    public void deleteTaxLine(Long id) {
        taxLineRepository.deleteById(id);
    }

    public Map<String, Object> getTaxSummary(Double grossAnnualIncome) {
        double gross = (grossAnnualIncome != null && grossAnnualIncome > 0) ? grossAnnualIncome : 1080000.0;

        Double ded80CRaw = taxLineRepository.sumDeductionsBySectionNative("80C");
        double ded80C = Math.min(ded80CRaw != null ? ded80CRaw : 150000.0, 150000.0);

        Double ded80DRaw = taxLineRepository.sumDeductionsBySectionNative("80D");
        double ded80D = Math.min(ded80DRaw != null ? ded80DRaw : 25000.0, 75000.0);

        Double ded80CCDRaw = taxLineRepository.sumDeductionsBySectionNative("80CCD(1B)");
        double ded80CCD = Math.min(ded80CCDRaw != null ? ded80CCDRaw : 35000.0, 50000.0);

        Double dedHRARaw = taxLineRepository.sumDeductionsBySectionNative("HRA");
        double dedHRA = (dedHRARaw != null) ? dedHRARaw : 67000.0;

        double totalDeductions = ded80C + ded80D + ded80CCD + dedHRA;

        // Old Regime: ₹50,000 Standard Deduction + Itemized Deductions
        double oldTaxable = Math.max(0.0, gross - 50000.0 - totalDeductions);
        double oldTax = calculateOldRegimeTax(oldTaxable);

        // New Regime: ₹75,000 Standard Deduction (Budget 2024 revised slabs)
        double newTaxable = Math.max(0.0, gross - 75000.0);
        double newTax = calculateNewRegimeTax(newTaxable);

        Map<String, Object> summary = new HashMap<>();
        summary.put("grossAnnualIncome", gross);
        summary.put("totalMatchedDeductions", totalDeductions);
        summary.put("deduction80C", ded80C);
        summary.put("deduction80D", ded80D);
        summary.put("deduction80CCD", ded80CCD);
        summary.put("deductionHRA", dedHRA);
        summary.put("oldRegimeTaxLiability", oldTax);
        summary.put("newRegimeTaxLiability", newTax);
        summary.put("recommendedRegime", newTax <= oldTax ? "New Tax Regime (Section 115BAC)" : "Old Tax Regime");
        summary.put("taxSavingsDelta", Math.abs(oldTax - newTax));
        return summary;
    }

    private double calculateOldRegimeTax(double taxable) {
        if (taxable <= 500000) return 0.0;
        double tax = 0.0;
        if (taxable > 1000000) {
            tax += (taxable - 1000000) * 0.30;
            tax += 500000 * 0.20;
            tax += 250000 * 0.05;
        } else if (taxable > 500000) {
            tax += (taxable - 500000) * 0.20;
            tax += 250000 * 0.05;
        }
        return Math.round(tax * 1.04);
    }

    private double calculateNewRegimeTax(double taxable) {
        if (taxable <= 700000) return 0.0;
        double tax = 0.0;
        if (taxable > 1500000) {
            tax += (taxable - 1500000) * 0.30;
            tax += 300000 * 0.20;
            tax += 200000 * 0.15;
            tax += 300000 * 0.10;
            tax += 400000 * 0.05;
        } else if (taxable > 1200000) {
            tax += (taxable - 1200000) * 0.20;
            tax += 200000 * 0.15;
            tax += 300000 * 0.10;
            tax += 400000 * 0.05;
        } else if (taxable > 1000000) {
            tax += (taxable - 1000000) * 0.15;
            tax += 300000 * 0.10;
            tax += 400000 * 0.05;
        } else if (taxable > 700000) {
            tax += (taxable - 700000) * 0.10;
            tax += 400000 * 0.05;
        } else if (taxable > 300000) {
            tax += (taxable - 300000) * 0.05;
        }
        return Math.round(tax * 1.04);
    }
}
