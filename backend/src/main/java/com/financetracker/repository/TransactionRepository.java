package com.financetracker.repository;

import com.financetracker.model.Transaction;
import com.financetracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserOrderByTransactionDateDesc(User user);
    List<Transaction> findByUserAndTypeAndCategory(User user, Transaction.TransactionType type, String category);
    List<Transaction> findByUserAndTransactionDateBetween(User user, LocalDate start, LocalDate end);
    Optional<Transaction> findByIdAndUser(Long id, User user);

    @Query("SELECT t.category, SUM(t.amount) FROM Transaction t WHERE t.user = :user AND t.type = 'EXPENSE' GROUP BY t.category")
    List<Object[]> findExpensesByCategory(@Param("user") User user);

    @Query("SELECT MONTH(t.transactionDate), SUM(t.amount) FROM Transaction t WHERE t.user = :user AND t.type = :type AND YEAR(t.transactionDate) = :year GROUP BY MONTH(t.transactionDate)")
    List<Object[]> findMonthlyTotals(@Param("user") User user, @Param("type") Transaction.TransactionType type, @Param("year") int year);
}
