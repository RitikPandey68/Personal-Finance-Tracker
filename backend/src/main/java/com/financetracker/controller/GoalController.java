package com.financetracker.controller;

import com.financetracker.model.Goal;
import com.financetracker.service.GoalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/goals")
@CrossOrigin(origins = "*")
public class GoalController {

    private final GoalService goalService;

    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    @GetMapping
    public ResponseEntity<List<Goal>> getAllGoals() {
        return ResponseEntity.ok(goalService.getAllGoals());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Goal> getGoalById(@PathVariable Long id) {
        return ResponseEntity.ok(goalService.getGoalById(id));
    }

    @PostMapping
    public ResponseEntity<Goal> createGoal(@RequestBody Goal goal) {
        return ResponseEntity.status(HttpStatus.CREATED).body(goalService.createGoal(goal));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Goal> updateGoal(@PathVariable Long id, @RequestBody Goal goal) {
        goal.setId(id);
        return ResponseEntity.ok(goalService.createGoal(goal));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGoal(@PathVariable Long id) {
        goalService.deleteGoal(id);
        return ResponseEntity.ok(Map.of("message", "Goal deleted successfully"));
    }

    @PostMapping("/{id}/contribute")
    public ResponseEntity<Goal> contributeToGoal(@PathVariable Long id, @RequestBody Map<String, Double> body) {
        Double amount = body.getOrDefault("amount", 0.0);
        return ResponseEntity.ok(goalService.contributeToGoal(id, amount));
    }

    @GetMapping("/{id}/forecast")
    public ResponseEntity<?> getGoalForecast(@PathVariable Long id) {
        Goal goal = goalService.getGoalById(id);
        double gap = goal.getTargetAmount() - goal.getCurrentAmount();
        double requiredMonthly = Math.round((gap / 12.0) * 100.0) / 100.0;
        return ResponseEntity.ok(Map.of(
            "goalTitle", goal.getTitle(),
            "targetAmount", goal.getTargetAmount(),
            "currentAmount", goal.getCurrentAmount(),
            "remainingGap", gap,
            "recommendedMonthlyContribution", requiredMonthly,
            "targetDeadline", goal.getDeadline()
        ));
    }

    @GetMapping("/summary")
    public ResponseEntity<?> getGoalsSummary() {
        return ResponseEntity.ok(goalService.getGoalsSummary());
    }
}
