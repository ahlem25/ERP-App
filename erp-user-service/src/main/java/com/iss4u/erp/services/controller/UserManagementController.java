package com.iss4u.erp.services.controller;

import com.iss4u.erp.services.modules.core.domain.dto.user.request.UserRequest;
import com.iss4u.erp.services.modules.core.domain.dto.user.response.UserListResponse;
import com.iss4u.erp.services.modules.core.domain.dto.user.response.UserResponse;
import com.iss4u.erp.services.service.KeycloakUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
@Slf4j
public class UserManagementController {
    
    @Autowired
    private KeycloakUserService keycloakUserService;
    
    /**
     * Récupère la liste des utilisateurs avec pagination et recherche
     */
    @GetMapping
    public ResponseEntity<UserListResponse> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search) {
        
        try {
            log.info("Fetching users - page: {}, size: {}, search: {}", page, size, search);
            
            UserListResponse response = keycloakUserService.getAllUsers(page, size, search);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("Error fetching users", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Récupère un utilisateur par ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable String id) {
        try {
            log.info("Fetching user with ID: {}", id);
            
            UserResponse user = keycloakUserService.getUserById(id);
            
            return ResponseEntity.ok(user);
            
        } catch (Exception e) {
            log.error("Error fetching user with ID: {}", id, e);
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Crée un nouvel utilisateur
     */
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest) {
        try {
            log.info("Creating new user: {}", userRequest.getEmail());
            
            UserResponse createdUser = keycloakUserService.createUser(userRequest);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
            
        } catch (Exception e) {
            log.error("Error creating user", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Met à jour un utilisateur
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable String id,
            @Valid @RequestBody UserRequest userRequest) {
        
        try {
            log.info("Updating user with ID: {}", id);
            
            UserResponse updatedUser = keycloakUserService.updateUser(id, userRequest);
            
            return ResponseEntity.ok(updatedUser);
            
        } catch (Exception e) {
            log.error("Error updating user with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Supprime un utilisateur
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        try {
            log.info("Deleting user with ID: {}", id);
            
            keycloakUserService.deleteUser(id);
            
            return ResponseEntity.noContent().build();
            
        } catch (Exception e) {
            log.error("Error deleting user with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Active/Désactive un utilisateur
     */
    @PatchMapping("/{id}/toggle-status")
    public ResponseEntity<UserResponse> toggleUserStatus(@PathVariable String id) {
        try {
            log.info("Toggling status for user with ID: {}", id);
            
            // Récupérer l'utilisateur actuel
            UserResponse currentUser = keycloakUserService.getUserById(id);
            
            // Créer une requête de mise à jour avec le statut inversé
            UserRequest updateRequest = new UserRequest();
            updateRequest.setFirstName(currentUser.getFirstName());
            updateRequest.setLastName(currentUser.getLastName());
            updateRequest.setEmail(currentUser.getEmail());
            updateRequest.setUsername(currentUser.getUsername());
            updateRequest.setEnabled(!currentUser.getEnabled());
            updateRequest.setEmailVerified(currentUser.getEmailVerified());
            updateRequest.setRoles(currentUser.getRoles());
            
            UserResponse updatedUser = keycloakUserService.updateUser(id, updateRequest);
            
            return ResponseEntity.ok(updatedUser);
            
        } catch (Exception e) {
            log.error("Error toggling status for user with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Récupère les statistiques des utilisateurs
     */
    @GetMapping("/stats")
    public ResponseEntity<Object> getUserStats() {
        try {
            log.info("Fetching user statistics");
            
            // Récupérer tous les utilisateurs pour les statistiques
            UserListResponse allUsers = keycloakUserService.getAllUsers(0, Integer.MAX_VALUE, null);
            
            long totalUsers = allUsers.getTotalElements();
            long activeUsers = allUsers.getUsers().stream()
                    .mapToLong(user -> user.getEnabled() ? 1 : 0)
                    .sum();
            long inactiveUsers = totalUsers - activeUsers;
            
            // Créer l'objet de statistiques
            java.util.Map<String, Object> stats = new java.util.HashMap<>();
            stats.put("totalUsers", totalUsers);
            stats.put("activeUsers", activeUsers);
            stats.put("inactiveUsers", inactiveUsers);
            stats.put("activePercentage", totalUsers > 0 ? (activeUsers * 100.0 / totalUsers) : 0);
            
            return ResponseEntity.ok(stats);
            
        } catch (Exception e) {
            log.error("Error fetching user statistics", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
