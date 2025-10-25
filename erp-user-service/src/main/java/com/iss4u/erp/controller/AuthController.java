package com.iss4u.erp.controller;

import com.iss4u.erp.models.Utilisateur;
import com.iss4u.erp.services.service.UtilisateurService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/utilisateurs")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AuthController {

    private final UtilisateurService utilisateurService;

    /**
     * Endpoint public pour obtenir les informations de l'utilisateur connecté
     */
    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> getCurrentUser(Authentication authentication) {
        try {
            String email = authentication.getName();
            System.out.println("🔐 Utilisateur connecté: " + email);
            
            // Rechercher l'utilisateur par email
            List<Utilisateur> utilisateurs = utilisateurService.getAllUtilisateurs();
            Utilisateur utilisateur = utilisateurs.stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst()
                .orElse(null);
            
            if (utilisateur == null) {
                // Créer un utilisateur basé sur les informations JWT
                Map<String, Object> userInfo = new HashMap<>();
                userInfo.put("email", email);
                userInfo.put("authenticated", true);
                userInfo.put("roles", authentication.getAuthorities());
                
                return ResponseEntity.ok(userInfo);
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("user", utilisateur);
            response.put("authenticated", true);
            response.put("roles", authentication.getAuthorities());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la récupération de l'utilisateur: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "Erreur interne du serveur"));
        }
    }

    /**
     * Endpoint public pour vérifier le statut d'authentification
     */
    @GetMapping("/auth/status")
    public ResponseEntity<Map<String, Object>> getAuthStatus(Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        
        if (authentication != null && authentication.isAuthenticated()) {
            response.put("authenticated", true);
            response.put("email", authentication.getName());
            response.put("roles", authentication.getAuthorities());
        } else {
            response.put("authenticated", false);
        }
        
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint pour synchroniser un utilisateur Keycloak avec la base de données
     */
    @PostMapping("/sync")
    public ResponseEntity<Map<String, Object>> syncUser(@RequestBody Map<String, Object> userData, Authentication authentication) {
        try {
            String email = authentication.getName();
            System.out.println("🔄 Synchronisation de l'utilisateur: " + email);
            
            // Vérifier si l'utilisateur existe déjà
            List<Utilisateur> utilisateurs = utilisateurService.getAllUtilisateurs();
            Utilisateur existingUser = utilisateurs.stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst()
                .orElse(null);
            
            if (existingUser != null) {
                System.out.println("✅ Utilisateur déjà synchronisé: " + email);
                return ResponseEntity.ok(Map.of(
                    "message", "Utilisateur déjà synchronisé",
                    "user", existingUser
                ));
            }
            
            // Créer un nouvel utilisateur
            Utilisateur newUser = new Utilisateur();
            newUser.setEmail(email);
            newUser.setPrenom((String) userData.getOrDefault("firstName", "Utilisateur"));
            newUser.setNomFamille((String) userData.getOrDefault("lastName", "Keycloak"));
            newUser.setNomUtilisateur(email);
            newUser.setActif(true);
            
            // Sauvegarder l'utilisateur
            Utilisateur savedUser = utilisateurService.createUtilisateur(newUser);
            
            System.out.println("✅ Utilisateur synchronisé avec succès: " + email);
            
            return ResponseEntity.ok(Map.of(
                "message", "Utilisateur synchronisé avec succès",
                "user", savedUser
            ));
            
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la synchronisation: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "Erreur lors de la synchronisation"));
        }
    }
}
