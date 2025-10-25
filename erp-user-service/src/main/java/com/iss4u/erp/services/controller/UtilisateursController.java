package com.iss4u.erp.services.controller;

import com.iss4u.erp.services.modules.core.domain.models.Utilisateur;
import com.iss4u.erp.services.modules.core.domain.repository.UtilisateurRepository;
import com.iss4u.erp.services.service.KeycloakSyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/utilisateurs")
@RequiredArgsConstructor
public class UtilisateursController {

    private final UtilisateurRepository utilisateurRepository;
    private final KeycloakSyncService keycloakSyncService;

    /**
     * Récupérer tous les utilisateurs
     */
    @GetMapping
    public ResponseEntity<List<Utilisateur>> getAllUtilisateurs() {
        List<Utilisateur> utilisateurs = utilisateurRepository.findAll();
        return ResponseEntity.ok(utilisateurs);
    }

    /**
     * Récupérer un utilisateur par ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Utilisateur> getUtilisateurById(@PathVariable Long id) {
        Optional<Utilisateur> utilisateur = utilisateurRepository.findById(id);
        return utilisateur.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Récupérer un utilisateur par email
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<Utilisateur> getUtilisateurByEmail(@PathVariable String email) {
        Optional<Utilisateur> utilisateur = utilisateurRepository.findByEmail(email);
        return utilisateur.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Créer un nouvel utilisateur
     */
    @PostMapping
    public ResponseEntity<Utilisateur> createUtilisateur(@RequestBody Utilisateur utilisateur) {
        try {
            Utilisateur savedUtilisateur = utilisateurRepository.save(utilisateur);
            return ResponseEntity.ok(savedUtilisateur);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Mettre à jour un utilisateur
     */
    @PutMapping("/{id}")
    public ResponseEntity<Utilisateur> updateUtilisateur(@PathVariable Long id, @RequestBody Utilisateur utilisateur) {
        if (!utilisateurRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        utilisateur.setId(id);
        Utilisateur updatedUtilisateur = utilisateurRepository.save(utilisateur);
        return ResponseEntity.ok(updatedUtilisateur);
    }

    /**
     * Supprimer un utilisateur
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable Long id) {
        if (!utilisateurRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        utilisateurRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Activer/Désactiver un utilisateur
     */
    @PatchMapping("/{id}/toggle-status")
    public ResponseEntity<Utilisateur> toggleUtilisateurStatus(@PathVariable Long id) {
        Optional<Utilisateur> utilisateurOpt = utilisateurRepository.findById(id);
        if (utilisateurOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        Utilisateur utilisateur = utilisateurOpt.get();
        utilisateur.setActif(!utilisateur.isActif());
        Utilisateur updatedUtilisateur = utilisateurRepository.save(utilisateur);
        return ResponseEntity.ok(updatedUtilisateur);
    }

    /**
     * Rechercher des utilisateurs
     */
    @GetMapping("/search")
    public ResponseEntity<List<Utilisateur>> searchUtilisateurs(@RequestParam String q) {
        // Implémentation basique de recherche
        List<Utilisateur> utilisateurs = utilisateurRepository.findAll().stream()
                .filter(u -> u.getPrenom().toLowerCase().contains(q.toLowerCase()) ||
                           u.getNomFamille().toLowerCase().contains(q.toLowerCase()) ||
                           u.getEmail().toLowerCase().contains(q.toLowerCase()) ||
                           u.getNomUtilisateur().toLowerCase().contains(q.toLowerCase()))
                .toList();
        return ResponseEntity.ok(utilisateurs);
    }

    /**
     * Synchroniser avec Keycloak
     */
    @PostMapping("/sync-keycloak")
    public ResponseEntity<String> syncWithKeycloak() {
        try {
            keycloakSyncService.syncAllUsers();
            return ResponseEntity.ok("✅ Synchronisation des utilisateurs terminée avec succès");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body("❌ Erreur lors de la synchronisation: " + e.getMessage());
        }
    }

    /**
     * Obtenir les statistiques des utilisateurs
     */
    @GetMapping("/stats")
    public ResponseEntity<Object> getUtilisateursStats() {
        long totalUtilisateurs = utilisateurRepository.count();
        long utilisateursActifs = utilisateurRepository.findAll().stream()
                .mapToLong(u -> u.isActif() ? 1 : 0)
                .sum();
        
        return ResponseEntity.ok(new Object() {
            public final long total = totalUtilisateurs;
            public final long actifs = utilisateursActifs;
            public final long inactifs = totalUtilisateurs - utilisateursActifs;
        });
    }
}
