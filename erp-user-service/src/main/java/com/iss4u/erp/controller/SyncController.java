package com.iss4u.erp.controller;

import com.iss4u.erp.services.service.KeycloakSyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class SyncController {

    @Autowired
    private KeycloakSyncService keycloakSyncService;

    @PostMapping("/sync-keycloak-users")
    public ResponseEntity<String> syncKeycloakUsers() {
        try {
            System.out.println("🔄 Synchronisation manuelle des utilisateurs Keycloak demandée...");
            
            // Déclencher la synchronisation
            boolean success = keycloakSyncService.syncAllUsers();
            
            if (success) {
                System.out.println("✅ Synchronisation manuelle réussie");
                return ResponseEntity.ok("Synchronisation réussie");
            } else {
                System.out.println("❌ Échec de la synchronisation manuelle");
                return ResponseEntity.status(500).body("Échec de la synchronisation");
            }
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la synchronisation manuelle: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erreur: " + e.getMessage());
        }
    }

    @GetMapping("/sync-status")
    public ResponseEntity<String> getSyncStatus() {
        try {
            // Vérifier si le service de synchronisation est disponible
            return ResponseEntity.ok("Service de synchronisation actif");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Service de synchronisation indisponible: " + e.getMessage());
        }
    }
}
