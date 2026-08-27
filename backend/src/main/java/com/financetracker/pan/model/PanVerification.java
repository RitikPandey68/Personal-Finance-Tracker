package com.financetracker.pan.model;

import com.financetracker.model.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "pan_verifications")
@Data
@NoArgsConstructor
public class PanVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "pan_number", nullable = false, length = 10)
    private String panNumber;

    @Column(name = "verified_name")
    private String verifiedName;

    @Column(name = "verification_status")
    private String verificationStatus; // "VERIFIED", "PENDING", "FAILED"

    @Column(name = "verified_at")
    private LocalDateTime verifiedAt;

    public PanVerification(User user, String panNumber, String verifiedName, String verificationStatus) {
        this.user = user;
        this.panNumber = panNumber;
        this.verifiedName = verifiedName;
        this.verificationStatus = verificationStatus;
        this.verifiedAt = LocalDateTime.now();
    }
}
