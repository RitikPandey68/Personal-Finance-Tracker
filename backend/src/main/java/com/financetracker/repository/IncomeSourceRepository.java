package com.financetracker.repository;

import com.financetracker.model.IncomeSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeSourceRepository extends JpaRepository<IncomeSource, Long> {

    @Query(value = "SELECT COALESCE(SUM(monthly_amount), 0.0) FROM income_sources WHERE verified = true", nativeQuery = true)
    Double sumVerifiedMonthlyIncomeNative();
}
