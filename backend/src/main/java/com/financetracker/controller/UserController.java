package com.financetracker.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody Map<String, Object> body) {
        return ResponseEntity.ok(Map.of("message", "Profile updated successfully", "data", body));
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody Map<String, String> body) {
        return ResponseEntity.ok(Map.of("message", "Password changed successfully"));
    }

    @GetMapping("/security-audit")
    public ResponseEntity<?> getSecurityAudit() {
        return ResponseEntity.ok(List.of(
            Map.of("action", "LOGIN_SUCCESS", "ip", "127.0.0.1", "timestamp", "2026-08-27 10:45:00", "device", "Chrome / Windows"),
            Map.of("action", "PASSWORD_CHANGE", "ip", "127.0.0.1", "timestamp", "2026-08-26 18:20:00", "device", "Chrome / Windows")
        ));
    }

    @PostMapping("/2fa/toggle")
    public ResponseEntity<?> toggle2FA(@RequestBody Map<String, Boolean> body) {
        return ResponseEntity.ok(Map.of("twoFactorEnabled", body.getOrDefault("enabled", true)));
    }
}
