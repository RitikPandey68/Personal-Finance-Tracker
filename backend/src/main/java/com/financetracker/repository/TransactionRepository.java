package com.financetracker.repository;

import com.financetracker.model.Transaction;
import com.financetracker.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByUserOrderByTransactionDateDesc(User user);

    Page<Transaction> findByUser(User user, Pageable pageable);

    List<Transaction> findByUserAndTypeAndCategory(User user, Transaction.TransactionType type, String category);

    List<Transaction> findByTransactionDateBetween(LocalDate start, LocalDate end);

    List<Transaction> findByUserAndTransactionDateBetween(User user, LocalDate start, LocalDate end);

    java.util.Optional<Transaction> findByIdAndUser(Long id, User user);

    // --- NATIVE SQL QUERIES ---
    @Query(value = "SELECT COALESCE(SUM(amount), 0.0) FROM transactions WHERE type = :type", nativeQuery = true)
    Double sumAmountByTypeNative(@Param("type") String type);

    @Query(value = "SELECT category, SUM(amount) as total FROM transactions WHERE type = 'EXPENSE' GROUP BY category ORDER BY total DESC", nativeQuery = true)
    List<Map<String, Object>> findCategoryExpenseBreakdownNative();

    @Query(value = "SELECT EXTRACT(MONTH FROM transaction_date) as month_num, type, SUM(amount) as total " +
                   "FROM transactions GROUP BY EXTRACT(MONTH FROM transaction_date), type ORDER BY month_num ASC", nativeQuery = true)
    List<Map<String, Object>> findMonthlyCashFlowNative();

    @Query(value = "SELECT * FROM transactions WHERE recurring != 'none' AND recurring IS NOT NULL", nativeQuery = true)
    List<Transaction> findRecurringTransactionsNative();

    @Query(value = "SELECT * FROM transactions WHERE LOWER(description) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(category) LIKE LOWER(CONCAT('%', :query, '%'))", nativeQuery = true)
    List<Transaction> searchTransactionsNative(@Param("query") String query);

    @Query(value = "SELECT COUNT(*) FROM transactions", nativeQuery = true)
    Long countAllTransactionsNative();
}
