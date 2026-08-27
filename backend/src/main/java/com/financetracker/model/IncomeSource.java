package com.financetracker.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "income_sources")
@Data
@NoArgsConstructor
public class IncomeSource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false, length = 50)
    private String category;

    @Column(name = "monthly_amount", nullable = false)
    private Double monthlyAmount;

    @Column(name = "proof_doc", length = 100)
    private String proofDoc;

    private boolean verified = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public IncomeSource(String name, String category, Double monthlyAmount, String proofDoc, boolean verified) {
        this.name = name;
        this.category = category;
        this.monthlyAmount = monthlyAmount;
        this.proofDoc = proofDoc;
        this.verified = verified;
        this.createdAt = LocalDateTime.now();
    }
}
