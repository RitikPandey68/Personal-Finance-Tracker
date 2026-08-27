package com.financetracker.pan.repository;

import com.financetracker.model.User;
import com.financetracker.pan.model.PanVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PanVerificationRepository extends JpaRepository<PanVerification, Long> {
    Optional<PanVerification> findByUser(User user);
}
