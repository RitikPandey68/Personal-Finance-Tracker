package com.financetracker.pan;

import com.financetracker.pan.model.PanVerification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/pan")
@CrossOrigin(origins = "*")
public class PanController {

    @Autowired private PanVerificationService panService;

    @PostMapping("/verify")
    public ResponseEntity<PanVerification> verify(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody Map<String, String> request
    ) {
        String email = userDetails != null ? userDetails.getUsername() : "ritik@example.com";
        String pan = request.getOrDefault("panNumber", "");
        String name = request.getOrDefault("fullName", "Ritik Pandey");
        return ResponseEntity.ok(panService.verifyPan(email, pan, name));
    }

    @GetMapping("/status")
    public ResponseEntity<PanVerification> getStatus(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String email = userDetails != null ? userDetails.getUsername() : "ritik@example.com";
        return ResponseEntity.of(panService.getPanVerification(email));
    }
}
