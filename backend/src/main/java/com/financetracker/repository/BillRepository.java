package com.financetracker.repository;

import com.financetracker.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

    List<Bill> findByPaidFalse();

    @Query(value = "SELECT COALESCE(SUM(amount), 0.0) FROM bills WHERE is_paid = false", nativeQuery = true)
    Double sumUpcomingUnpaidBillsNative();
}
