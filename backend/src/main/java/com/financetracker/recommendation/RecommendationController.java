package com.financetracker.recommendation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/recommendation")
@CrossOrigin(origins = "*")
public class RecommendationController {

    @Autowired private RecommendationService recommendationService;

    @PostMapping("/assess")
    public ResponseEntity<Map<String, Object>> assessAndRecommend(
            @RequestBody RiskProfileRequest request
    ) {
        return ResponseEntity.ok(recommendationService.generateRecommendations(request));
    }
}
