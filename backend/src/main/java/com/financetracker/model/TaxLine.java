package com.financetracker.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "tax_lines")
@Data
@NoArgsConstructor
public class TaxLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, length = 255)
    private String description;

    @Column(nullable = false, length = 50)
    private String section;

    @Column(nullable = false)
    private Double amount;

    @Column(name = "proof_doc", length = 100)
    private String proofDoc;

    @Column(name = "financial_year", length = 20)
    private String financialYear = "FY 2024-25";

    private boolean verified = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public TaxLine(String description, String section, Double amount, String proofDoc, String financialYear, boolean verified) {
        this.description = description;
        this.section = section;
        this.amount = amount;
        this.proofDoc = proofDoc;
        this.financialYear = financialYear;
        this.verified = verified;
        this.createdAt = LocalDateTime.now();
    }
}
