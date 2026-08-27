package com.financetracker.controller;

import com.financetracker.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        return ResponseEntity.ok(authService.register(
            request.get("email"),
            request.get("password"),
            request.get("fullName")
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        return ResponseEntity.ok(authService.login(
            request.get("email"),
            request.get("password")
        ));
    }

    /**
     * Web OAuth2 Social Login (Google / GitHub)
     */
    /**
     * Check if a Google/Social Account already exists in database
     */
    @PostMapping("/oauth2/check-account")
    public ResponseEntity<?> checkAccount(@RequestBody Map<String, String> request) {
        return ResponseEntity.ok(authService.checkAccountStatus(
            request.get("email"),
            request.getOrDefault("provider", "google")
        ));
    }

    /**
     * Web OAuth2 Social Login / Sign-up Callback (Google / GitHub)
     * Auto registers or signs in with device permissions & financial onboarding profile
     */
    @PostMapping("/oauth2/callback")
    public ResponseEntity<?> oauth2Callback(@RequestBody Map<String, Object> request) {
        String provider = request.containsKey("provider") ? String.valueOf(request.get("provider")) : "google";
        String code = request.containsKey("code") ? String.valueOf(request.get("code")) : null;
        String email = request.containsKey("email") ? String.valueOf(request.get("email")) : null;
        String name = request.containsKey("name") ? String.valueOf(request.get("name")) : null;

        return ResponseEntity.ok(authService.handleOAuth2Callback(
            provider,
            code,
            email,
            name,
            request
        ));
    }

    /**
     * Google Identity Services (GSI) Real ID Token Verification
     */
    @PostMapping("/oauth2/google-token")
    public ResponseEntity<?> googleTokenLogin(@RequestBody Map<String, Object> request) {
        String idToken = request.containsKey("idToken") ? String.valueOf(request.get("idToken")) :
                request.containsKey("credential") ? String.valueOf(request.get("credential")) : null;
        return ResponseEntity.ok(authService.handleGoogleIdTokenLogin(idToken, request));
    }

    /**
     * Mobile OAuth2 PKCE Step 1: Authorize Mobile App with Code Challenge
     */
    @PostMapping("/oauth2/mobile-authorize")
    public ResponseEntity<?> mobileAuthorize(@RequestBody Map<String, String> request) {
        return ResponseEntity.ok(authService.generateMobilePkceAuthCode(
            request.getOrDefault("clientId", "financeflow-mobile-android"),
            request.getOrDefault("codeChallenge", "E9Melhoa2OwvFrGMTJguCH5A_49q4UEd"),
            request.getOrDefault("codeChallengeMethod", "S256"),
            request.getOrDefault("redirectUri", "financeflow://oauth2/callback"),
            request.get("email")
        ));
    }

    /**
     * Mobile OAuth2 PKCE Step 2: Exchange Authorization Code + Verifier for Tokens
     */
    @PostMapping("/oauth2/mobile-token")
    public ResponseEntity<?> mobileToken(@RequestBody Map<String, String> request) {
        return ResponseEntity.ok(authService.exchangeMobilePkceToken(
            request.get("code"),
            request.getOrDefault("codeVerifier", "dBjftJeZ4CVP-mB92K27uhbUJU1p1r_wW1gFWFOEjXk"),
            request.getOrDefault("clientId", "financeflow-mobile-android"),
            request.getOrDefault("redirectUri", "financeflow://oauth2/callback")
        ));
    }

    /**
     * Mobile OTP Step 1: Send SMS OTP
     */
    @PostMapping("/mobile/send-otp")
    public ResponseEntity<?> sendMobileOtp(@RequestBody Map<String, String> request) {
        return ResponseEntity.ok(authService.sendMobileOtp(request.get("phone")));
    }

    /**
     * Mobile OTP Step 2: Verify OTP and Auto Sign-In / Sign-Up
     */
    @PostMapping("/mobile/verify-otp")
    public ResponseEntity<?> verifyMobileOtp(@RequestBody Map<String, String> request) {
        return ResponseEntity.ok(authService.verifyMobileOtpAndLogin(
            request.get("phone"),
            request.get("otp"),
            request.get("fullName")
        ));
    }

    /**
     * Refresh JWT Token
     */
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {
        return ResponseEntity.ok(authService.refreshToken(request.get("refreshToken")));
    }

    /**
     * Secure Logout & Token Revocation
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout(jakarta.servlet.http.HttpServletRequest request,
                                    @RequestHeader(value = "Authorization", required = false) String authHeader) {
        String token = (authHeader != null && authHeader.startsWith("Bearer ")) ?
                authHeader.substring(7) : request.getHeader("Authorization");
        return ResponseEntity.ok(authService.logout(token));
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMe() {
        return ResponseEntity.ok(Map.of(
            "name", "Ritik Pandey",
            "email", "ritik@financeflow.com",
            "currency", "INR",
            "panVerified", true,
            "role", "ROLE_USER"
        ));
    }
}
