package com.financetracker.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "budgets")
@Data
@NoArgsConstructor
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, length = 100)
    private String category;

    @Column(length = 50)
    private String icon = "fa-chart-pie";

    @Column(length = 20)
    private String color = "#6366f1";

    @Column(name = "budgeted_amount", nullable = false)
    private Double budgetedAmount;

    @Column(name = "spent_amount")
    private Double spentAmount = 0.0;

    @Column(name = "period_month", length = 20)
    private String periodMonth;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public Budget(String category, String icon, String color, Double budgetedAmount, Double spentAmount, String periodMonth) {
        this.category = category;
        this.icon = icon;
        this.color = color;
        this.budgetedAmount = budgetedAmount;
        this.spentAmount = spentAmount;
        this.periodMonth = periodMonth;
        this.createdAt = LocalDateTime.now();
    }
}
