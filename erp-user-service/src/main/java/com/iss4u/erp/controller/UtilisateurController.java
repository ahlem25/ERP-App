package com.iss4u.erp.controller;

import com.iss4u.erp.models.Utilisateur;
import com.iss4u.erp.services.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/utilisateurs")
@CrossOrigin(origins = "*")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    @GetMapping
    public ResponseEntity<List<Utilisateur>> getAllUtilisateurs() {
        try {
            List<Utilisateur> utilisateurs = utilisateurService.getAllUtilisateurs();
            System.out.println("📊 Récupération de " + utilisateurs.size() + " utilisateurs");
            return ResponseEntity.ok(utilisateurs);
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la récupération des utilisateurs: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Utilisateur> getUtilisateurById(@PathVariable Long id) {
        try {
            Utilisateur utilisateur = utilisateurService.getUtilisateurById(id);
            if (utilisateur != null) {
                return ResponseEntity.ok(utilisateur);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la récupération de l'utilisateur " + id + ": " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<Utilisateur> createUtilisateur(@RequestBody Utilisateur utilisateur) {
        try {
            Utilisateur createdUtilisateur = utilisateurService.createUtilisateur(utilisateur);
            System.out.println("✅ Utilisateur créé: " + createdUtilisateur.getEmail());
            return ResponseEntity.ok(createdUtilisateur);
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la création de l'utilisateur: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Utilisateur> updateUtilisateur(@PathVariable Long id, @RequestBody Utilisateur utilisateur) {
        try {
            utilisateur.setId(id);
            Utilisateur updatedUtilisateur = utilisateurService.updateUtilisateur(utilisateur);
            if (updatedUtilisateur != null) {
                System.out.println("✅ Utilisateur mis à jour: " + updatedUtilisateur.getEmail());
                return ResponseEntity.ok(updatedUtilisateur);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la mise à jour de l'utilisateur " + id + ": " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable Long id) {
        try {
            boolean deleted = utilisateurService.deleteUtilisateur(id);
            if (deleted) {
                System.out.println("✅ Utilisateur supprimé: " + id);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la suppression de l'utilisateur " + id + ": " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Utilisateur>> searchUtilisateurs(@RequestParam String q) {
        try {
            List<Utilisateur> utilisateurs = utilisateurService.searchUtilisateurs(q);
            System.out.println("🔍 Recherche '" + q + "': " + utilisateurs.size() + " résultats");
            return ResponseEntity.ok(utilisateurs);
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la recherche: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @PatchMapping("/{id}/toggle-status")
    public ResponseEntity<Utilisateur> toggleUtilisateurStatus(@PathVariable Long id) {
        try {
            Utilisateur utilisateur = utilisateurService.toggleUtilisateurStatus(id);
            if (utilisateur != null) {
                System.out.println("🔄 Statut de l'utilisateur " + id + " modifié: " + (utilisateur.isActif() ? "Actif" : "Inactif"));
                return ResponseEntity.ok(utilisateur);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.err.println("❌ Erreur lors du changement de statut de l'utilisateur " + id + ": " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    // Endpoint de login simple
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> loginRequest) {
        try {
            String email = loginRequest.get("email");
            String password = loginRequest.get("password");
            
            System.out.println("🔐 Tentative de connexion pour: " + email);
            
            // Rechercher l'utilisateur par email
            List<Utilisateur> utilisateurs = utilisateurService.getAllUtilisateurs();
            Utilisateur utilisateur = utilisateurs.stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst()
                .orElse(null);
            
            if (utilisateur == null) {
                System.out.println("❌ Utilisateur non trouvé: " + email);
                return ResponseEntity.status(401).body(Map.of("error", "Email ou mot de passe incorrect"));
            }
            
            // Vérifier le mot de passe (comparaison simple pour le moment)
            if (!password.equals(utilisateur.getPassword())) {
                System.out.println("❌ Mot de passe incorrect pour: " + email);
                return ResponseEntity.status(401).body(Map.of("error", "Email ou mot de passe incorrect"));
            }
            
            // Vérifier que l'utilisateur est actif
            if (utilisateur.getActif() == null || !utilisateur.getActif()) {
                System.out.println("❌ Utilisateur inactif: " + email);
                return ResponseEntity.status(401).body(Map.of("error", "Compte utilisateur inactif"));
            }
            
            // Générer un token simple (pour le moment)
            String token = "token_" + utilisateur.getId() + "_" + System.currentTimeMillis();
            
            System.out.println("✅ Connexion réussie pour: " + email);
            
            Map<String, Object> response = new HashMap<>();
            response.put("user", utilisateur);
            response.put("token", token);
            response.put("message", "Connexion réussie");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la connexion: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "Erreur interne du serveur"));
        }
    }
}
