package com.iss4u.erp.services.controller;

import com.iss4u.erp.services.modules.core.domain.dto.auth.*;
import com.iss4u.erp.services.modules.core.domain.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;
    
    /**
     * Connexion utilisateur
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        AuthResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Inscription utilisateur
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest) {
        AuthResponse response = authService.register(registerRequest);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Déconnexion utilisateur
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token) {
        authService.logout(token);
        return ResponseEntity.ok().build();
    }
    
    /**
     * Rafraîchir le token
     */
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody String refreshToken) {
        AuthResponse response = authService.refreshToken(refreshToken);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Mot de passe oublié
     */
    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        authService.forgotPassword(forgotPasswordRequest);
        return ResponseEntity.ok().build();
    }
    
    /**
     * Réinitialiser le mot de passe
     */
    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        authService.resetPassword(resetPasswordRequest);
        return ResponseEntity.ok().build();
    }
    
    /**
     * Changer le mot de passe
     */
    @PutMapping("/change-password")
    public ResponseEntity<Void> changePassword(
            @RequestHeader("Authorization") String token,
            @RequestBody ChangePasswordRequest changePasswordRequest) {
        // Extraire l'ID utilisateur du token
        // authService.changePassword(userId, changePasswordRequest);
        return ResponseEntity.ok().build();
    }
    
    /**
     * Vérifier l'email
     */
    @GetMapping("/verify-email")
    public ResponseEntity<Void> verifyEmail(@RequestParam String token) {
        authService.verifyEmail(token);
        return ResponseEntity.ok().build();
    }
    
    /**
     * Obtenir le profil utilisateur actuel
     */
    @GetMapping("/me")
    public ResponseEntity<Object> getCurrentUser(@RequestHeader("Authorization") String token) {
        // authService.getCurrentUser(token);
        return ResponseEntity.ok().build();
    }
}
