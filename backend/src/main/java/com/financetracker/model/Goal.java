package com.financetracker.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "goals")
@Data
@NoArgsConstructor
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(length = 20)
    private String emoji = "🎯";

    @Column(name = "target_amount", nullable = false)
    private Double targetAmount;

    @Column(name = "current_amount")
    private Double currentAmount = 0.0;

    @Column(length = 50)
    private String deadline;

    @Column(length = 50)
    private String category = "Savings";

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public Goal(String title, String emoji, Double targetAmount, Double currentAmount, String deadline, String category) {
        this.title = title;
        this.emoji = emoji;
        this.targetAmount = targetAmount;
        this.currentAmount = currentAmount;
        this.deadline = deadline;
        this.category = category;
        this.createdAt = LocalDateTime.now();
    }
}
