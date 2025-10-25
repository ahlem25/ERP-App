package com.iss4u.erp.services.modules.core.domain.service;

import com.iss4u.erp.services.modules.core.domain.dto.auth.*;
import com.iss4u.erp.services.modules.core.domain.models.Utilisateur;

public interface AuthService {
    
    /**
     * Authentifier un utilisateur
     */
    AuthResponse login(LoginRequest loginRequest);
    
    /**
     * Enregistrer un nouvel utilisateur
     */
    AuthResponse register(RegisterRequest registerRequest);
    
    /**
     * Déconnexion d'un utilisateur
     */
    void logout(String token);
    
    /**
     * Rafraîchir le token d'authentification
     */
    AuthResponse refreshToken(String refreshToken);
    
    /**
     * Demander une réinitialisation de mot de passe
     */
    void forgotPassword(ForgotPasswordRequest forgotPasswordRequest);
    
    /**
     * Réinitialiser le mot de passe
     */
    void resetPassword(ResetPasswordRequest resetPasswordRequest);
    
    /**
     * Changer le mot de passe
     */
    void changePassword(Long userId, ChangePasswordRequest changePasswordRequest);
    
    /**
     * Vérifier l'email d'un utilisateur
     */
    void verifyEmail(String token);
    
    /**
     * Obtenir l'utilisateur actuel à partir du token
     */
    Utilisateur getCurrentUser(String token);
    
    /**
     * Vérifier si un token est valide
     */
    boolean isTokenValid(String token);
}
