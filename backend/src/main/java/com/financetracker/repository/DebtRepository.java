package com.financetracker.repository;

import com.financetracker.model.Debt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DebtRepository extends JpaRepository<Debt, Long> {

    @Query(value = "SELECT COALESCE(SUM(remaining_balance), 0.0) FROM debts", nativeQuery = true)
    Double sumTotalOutstandingDebtNative();

    @Query(value = "SELECT COALESCE(SUM(monthly_emi), 0.0) FROM debts", nativeQuery = true)
    Double sumTotalMonthlyEmiNative();
}
