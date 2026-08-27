package com.financetracker.service;

import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenBlacklistService {

    // Thread-safe in-memory blacklist (or backed by Redis when distributed)
    private final Set<String> blacklistedTokens = ConcurrentHashMap.newKeySet();

    public void blacklistToken(String token) {
        if (token != null && !token.isBlank()) {
            String cleanedToken = token.startsWith("Bearer ") ? token.substring(7).trim() : token.trim();
            blacklistedTokens.add(cleanedToken);
        }
    }

    public boolean isBlacklisted(String token) {
        if (token == null || token.isBlank()) {
            return false;
        }
        String cleanedToken = token.startsWith("Bearer ") ? token.substring(7).trim() : token.trim();
        return blacklistedTokens.contains(cleanedToken);
    }
}
