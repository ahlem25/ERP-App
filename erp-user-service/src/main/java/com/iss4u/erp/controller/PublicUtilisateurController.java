package com.iss4u.erp.controller;

import com.iss4u.erp.models.Utilisateur;
import com.iss4u.erp.services.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public/api/utilisateurs")
@CrossOrigin(origins = "*")
public class PublicUtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    @GetMapping
    public ResponseEntity<List<Utilisateur>> getAllUtilisateurs() {
        try {
            List<Utilisateur> utilisateurs = utilisateurService.getAllUtilisateurs();
            System.out.println("📊 [PUBLIC] Récupération de " + utilisateurs.size() + " utilisateurs");
            return ResponseEntity.ok(utilisateurs);
        } catch (Exception e) {
            System.err.println("❌ [PUBLIC] Erreur lors de la récupération des utilisateurs: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Utilisateur> getUtilisateurById(@PathVariable Long id) {
        try {
            Utilisateur utilisateur = utilisateurService.getUtilisateurById(id);
            if (utilisateur != null) {
                System.out.println("📊 [PUBLIC] Utilisateur récupéré: " + utilisateur.getEmail());
                return ResponseEntity.ok(utilisateur);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.err.println("❌ [PUBLIC] Erreur lors de la récupération de l'utilisateur " + id + ": " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Utilisateur>> searchUtilisateurs(@RequestParam String q) {
        try {
            List<Utilisateur> utilisateurs = utilisateurService.searchUtilisateurs(q);
            System.out.println("🔍 [PUBLIC] Recherche '" + q + "': " + utilisateurs.size() + " résultats");
            return ResponseEntity.ok(utilisateurs);
        } catch (Exception e) {
            System.err.println("❌ [PUBLIC] Erreur lors de la recherche: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
}
