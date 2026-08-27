package com.financetracker.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    private String phone;

    private String dob;

    private String address;

    private String city;

    private String location;

    @Column(name = "pan_number")
    private String panNumber;

    private String occupation;

    @Column(name = "monthly_income")
    private Double monthlyIncome = 90000.0;

    @Column(name = "monthly_budget")
    private Double monthlyBudget = 45000.0;

    @Column(name = "financial_goal")
    private String primaryFinancialGoal;

    @Column(name = "oauth_provider")
    private String oauthProvider = "LOCAL";

    @Column(name = "profile_image")
    private String profileImage;

    private String currency = "INR";

    private boolean enabled = true;

    @Column(name = "email_verified")
    private boolean emailVerified = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "last_login")
    private LocalDateTime lastLogin;
}

