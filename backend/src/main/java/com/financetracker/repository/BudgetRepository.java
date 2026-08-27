package com.financetracker.repository;

import com.financetracker.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {

    List<Budget> findByPeriodMonth(String periodMonth);

    @Query(value = "SELECT COALESCE(SUM(budgeted_amount), 0.0) FROM budgets", nativeQuery = true)
    Double sumTotalBudgetedNative();

    @Query(value = "SELECT COALESCE(SUM(spent_amount), 0.0) FROM budgets", nativeQuery = true)
    Double sumTotalSpentNative();
}
