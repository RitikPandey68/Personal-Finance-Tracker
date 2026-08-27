package com.financetracker.service;

import com.financetracker.model.Goal;
import com.financetracker.repository.GoalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GoalService {

    private final GoalRepository goalRepository;

    public GoalService(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }

    public List<Goal> getAllGoals() {
        return goalRepository.findAll();
    }

    public Goal getGoalById(Long id) {
        return goalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Goal not found with ID: " + id));
    }

    @Transactional
    public Goal createGoal(Goal goal) {
        return goalRepository.save(goal);
    }

    @Transactional
    public Goal contributeToGoal(Long id, Double amount) {
        Goal goal = getGoalById(id);
        goal.setCurrentAmount(goal.getCurrentAmount() + amount);
        return goalRepository.save(goal);
    }

    @Transactional
    public void deleteGoal(Long id) {
        goalRepository.deleteById(id);
    }

    public Map<String, Object> getGoalsSummary() {
        Double totalTarget = goalRepository.sumTotalGoalTargetNative();
        Double totalSaved = goalRepository.sumTotalGoalSavedNative();
        Double overallProgress = totalTarget > 0 ? (totalSaved / totalTarget) * 100 : 0.0;

        Map<String, Object> summary = new HashMap<>();
        summary.put("totalTarget", totalTarget);
        summary.put("totalSaved", totalSaved);
        summary.put("remainingGap", totalTarget - totalSaved);
        summary.put("overallProgressPct", Math.round(overallProgress * 10.0) / 10.0);
        return summary;
    }
}
