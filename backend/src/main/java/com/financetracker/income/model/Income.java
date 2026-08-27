package com.financetracker.income.model;

import com.financetracker.model.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "incomes")
@Data
@NoArgsConstructor
public class Income {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String sourceName; // Salary, Freelancing, Investments, Other

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal monthlyAmount;

    @Column(name = "verification_doc_type")
    private String verificationDocType; // Salary Slip, Bank Statement, ITR

    @Column(name = "verified_status")
    private boolean verifiedStatus = true;

    public Income(User user, String sourceName, BigDecimal monthlyAmount, String docType) {
        this.user = user;
        this.sourceName = sourceName;
        this.monthlyAmount = monthlyAmount;
        this.verificationDocType = docType;
        this.verifiedStatus = true;
    }
}
