package com.financetracker.repository;

import com.financetracker.model.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {

    @Query(value = "SELECT COALESCE(SUM(target_amount), 0.0) FROM goals", nativeQuery = true)
    Double sumTotalGoalTargetNative();

    @Query(value = "SELECT COALESCE(SUM(current_amount), 0.0) FROM goals", nativeQuery = true)
    Double sumTotalGoalSavedNative();
}
