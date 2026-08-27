package com.financetracker.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "debts")
@Data
@NoArgsConstructor
public class Debt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;

    @Column(name = "remaining_balance", nullable = false)
    private Double remainingBalance;

    @Column(name = "interest_rate", nullable = false)
    private Double interestRate;

    @Column(name = "monthly_emi", nullable = false)
    private Double monthlyEmi;

    @Column(name = "due_date", length = 50)
    private String dueDate;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public Debt(String name, Double totalAmount, Double remainingBalance, Double interestRate, Double monthlyEmi, String dueDate) {
        this.name = name;
        this.totalAmount = totalAmount;
        this.remainingBalance = remainingBalance;
        this.interestRate = interestRate;
        this.monthlyEmi = monthlyEmi;
        this.dueDate = dueDate;
        this.createdAt = LocalDateTime.now();
    }
}
