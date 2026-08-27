package com.financetracker.repository;

import com.financetracker.model.TaxLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaxLineRepository extends JpaRepository<TaxLine, Long> {

    List<TaxLine> findBySection(String section);

    @Query(value = "SELECT COALESCE(SUM(amount), 0.0) FROM tax_lines WHERE section = :section", nativeQuery = true)
    Double sumDeductionsBySectionNative(@Param("section") String section);

    @Query(value = "SELECT COALESCE(SUM(amount), 0.0) FROM tax_lines", nativeQuery = true)
    Double sumTotalDeductionsNative();
}
