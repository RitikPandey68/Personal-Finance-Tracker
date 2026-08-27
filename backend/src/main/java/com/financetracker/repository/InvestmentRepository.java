package com.financetracker.repository;

import com.financetracker.model.Investment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface InvestmentRepository extends JpaRepository<Investment, Long> {

    @Query(value = "SELECT COALESCE(SUM(quantity * current_price), 0.0) FROM investments", nativeQuery = true)
    Double sumTotalCurrentPortfolioValueNative();

    @Query(value = "SELECT COALESCE(SUM(quantity * buy_price), 0.0) FROM investments", nativeQuery = true)
    Double sumTotalInvestedCapitalNative();

    @Query(value = "SELECT asset_type, SUM(quantity * current_price) as total_val FROM investments GROUP BY asset_type", nativeQuery = true)
    List<Map<String, Object>> findAssetAllocationNative();
}
