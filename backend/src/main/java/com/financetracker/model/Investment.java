package com.financetracker.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "investments")
@Data
@NoArgsConstructor
public class Investment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, length = 30)
    private String ticker;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(name = "asset_type", nullable = false, length = 50)
    private String assetType;

    @Column(nullable = false)
    private Double quantity;

    @Column(name = "buy_price", nullable = false)
    private Double buyPrice;

    @Column(name = "current_price", nullable = false)
    private Double currentPrice;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public Double getTotalValue() {
        return (quantity != null && currentPrice != null) ? quantity * currentPrice : 0.0;
    }

    public Double getTotalCost() {
        return (quantity != null && buyPrice != null) ? quantity * buyPrice : 0.0;
    }

    public Double getProfitLoss() {
        return getTotalValue() - getTotalCost();
    }

    public Double getReturnPercentage() {
        Double cost = getTotalCost();
        return (cost != null && cost > 0) ? ((getTotalValue() - cost) / cost) * 100 : 0.0;
    }

    public Investment(String ticker, String name, String assetType, Double quantity, Double buyPrice, Double currentPrice) {
        this.ticker = ticker;
        this.name = name;
        this.assetType = assetType;
        this.quantity = quantity;
        this.buyPrice = buyPrice;
        this.currentPrice = currentPrice;
        this.createdAt = LocalDateTime.now();
    }
}
