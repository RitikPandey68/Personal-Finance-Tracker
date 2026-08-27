package com.financetracker.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "bills")
@Data
@NoArgsConstructor
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false)
    private Double amount;

    @Column(name = "due_date", nullable = false, length = 50)
    private String dueDate;

    @Column(nullable = false, length = 50)
    private String category;

    @Column(length = 30)
    private String frequency = "monthly";

    @Column(name = "is_paid")
    private boolean paid = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public Bill(String name, Double amount, String dueDate, String category, String frequency, boolean paid) {
        this.name = name;
        this.amount = amount;
        this.dueDate = dueDate;
        this.category = category;
        this.frequency = frequency;
        this.paid = paid;
        this.createdAt = LocalDateTime.now();
    }
}
