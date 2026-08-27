package com.financetracker.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "reconciliation_records")
@Data
@NoArgsConstructor
public class ReconciliationRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "internal_id", nullable = false, length = 50)
    private String internalId;

    @Column(nullable = false, length = 255)
    private String description;

    @Column(name = "bank_ref", length = 100)
    private String bankRef;

    @Column(name = "gateway_ref", length = 100)
    private String gatewayRef;

    @Column(nullable = false)
    private Double amount;

    @Column(name = "bank_amount", nullable = false)
    private Double bankAmount;

    private Double delta = 0.0;

    @Column(nullable = false, length = 50)
    private String status;

    @Column(length = 255)
    private String reason;

    @Column(name = "is_exception")
    private boolean exception = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public ReconciliationRecord(String internalId, String description, String bankRef, String gatewayRef, Double amount, Double bankAmount, Double delta, String status, String reason, boolean exception) {
        this.internalId = internalId;
        this.description = description;
        this.bankRef = bankRef;
        this.gatewayRef = gatewayRef;
        this.amount = amount;
        this.bankAmount = bankAmount;
        this.delta = delta;
        this.status = status;
        this.reason = reason;
        this.exception = exception;
        this.createdAt = LocalDateTime.now();
    }
}
