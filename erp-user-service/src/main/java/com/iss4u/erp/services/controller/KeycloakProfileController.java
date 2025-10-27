package com.iss4u.erp.services.controller;

import com.iss4u.erp.services.modules.core.domain.dto.user.request.PasswordChangeRequest;
import com.iss4u.erp.services.modules.core.domain.dto.user.request.ProfileUpdateRequest;
import com.iss4u.erp.services.modules.core.domain.dto.user.response.UserResponse;
import com.iss4u.erp.services.modules.core.domain.exception.UserAlreadyExistsException;
import com.iss4u.erp.services.service.KeycloakProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/keycloak/profiles")
@Slf4j
public class KeycloakProfileController {
    
    @Autowired
    private KeycloakProfileService profileService;
    
    /**
     * Récupère le profil d'un utilisateur
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getProfile(@PathVariable String userId) {
        try {
            log.info("Fetching profile for user ID: {}", userId);
            
            UserResponse profile = profileService.getUserById(userId);
            
            return ResponseEntity.ok(profile);
            
        } catch (Exception e) {
            log.error("Error fetching profile for user ID: {}", userId, e);
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Met à jour le profil d'un utilisateur
     */
    @PutMapping("/{userId}")
    public ResponseEntity<?> updateProfile(
            @PathVariable String userId,
            @Valid @RequestBody ProfileUpdateRequest request) {
        
        try {
            log.info("Updating profile for user ID: {}", userId);
            
            UserResponse updatedProfile = profileService.updateProfile(userId, request);
            
            return ResponseEntity.ok(updatedProfile);
            
        } catch (UserAlreadyExistsException e) {
            log.error("User already exists during profile update: {}", e.getMessage());
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            errorResponse.put("error", "USER_ALREADY_EXISTS");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        } catch (Exception e) {
            log.error("Error updating profile for user ID: {}", userId, e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Erreur lors de la mise à jour du profil: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    /**
     * Change le mot de passe d'un utilisateur
     */
    @PostMapping("/{userId}/change-password")
    public ResponseEntity<?> changePassword(
            @PathVariable String userId,
            @Valid @RequestBody PasswordChangeRequest request) {
        
        try {
            log.info("Changing password for user ID: {}", userId);
            
            profileService.changePassword(userId, request);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Mot de passe changé avec succès");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("Error changing password for user ID: {}", userId, e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Erreur lors du changement de mot de passe: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}

