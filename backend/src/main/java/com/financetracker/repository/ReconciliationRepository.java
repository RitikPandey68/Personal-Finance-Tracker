package com.financetracker.repository;

import com.financetracker.model.ReconciliationRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReconciliationRepository extends JpaRepository<ReconciliationRecord, Long> {

    List<ReconciliationRecord> findByExceptionTrue();

    List<ReconciliationRecord> findByExceptionFalse();

    @Query(value = "SELECT COUNT(*) FROM reconciliation_records", nativeQuery = true)
    Long countTotalRecordsNative();

    @Query(value = "SELECT COUNT(*) FROM reconciliation_records WHERE is_exception = false", nativeQuery = true)
    Long countMatchedRecordsNative();

    @Query(value = "SELECT COUNT(*) FROM reconciliation_records WHERE is_exception = true", nativeQuery = true)
    Long countExceptionRecordsNative();

    @Query(value = "SELECT COALESCE(SUM(delta), 0.0) FROM reconciliation_records WHERE is_exception = true", nativeQuery = true)
    Double sumExceptionDeltaNative();
}
