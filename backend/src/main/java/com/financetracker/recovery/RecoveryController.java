package com.financetracker.recovery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/recovery")
@CrossOrigin(origins = "*")
public class RecoveryController {

    @Autowired private RecoveryService recoveryService;

    @PostMapping("/simulate")
    public ResponseEntity<Map<String, Object>> simulate(
            @RequestBody Map<String, Object> request
    ) {
        BigDecimal original = new BigDecimal(request.getOrDefault("originalValue", 100000).toString());
        BigDecimal current = new BigDecimal(request.getOrDefault("currentValue", 80000).toString());
        BigDecimal monthly = new BigDecimal(request.getOrDefault("monthlyInvestment", 5000).toString());

        return ResponseEntity.ok(recoveryService.simulateRecovery(original, current, monthly));
    }
}
