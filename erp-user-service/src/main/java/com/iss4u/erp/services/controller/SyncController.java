package com.iss4u.erp.services.controller;

import com.iss4u.erp.services.service.KeycloakSyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sync")
@RequiredArgsConstructor
public class SyncController {

    private final KeycloakSyncService keycloakSyncService;

    /**
     * Synchronise tous les utilisateurs de Keycloak vers la base de données ERP
     */
    @PostMapping("/users")
    public ResponseEntity<String> syncUsers() {
        try {
            keycloakSyncService.syncAllUsers();
            return ResponseEntity.ok("✅ Synchronisation des utilisateurs terminée avec succès");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body("❌ Erreur lors de la synchronisation: " + e.getMessage());
        }
    }

    /**
     * Vérifie le statut de la synchronisation
     */
    @GetMapping("/status")
    public ResponseEntity<String> getSyncStatus() {
        return ResponseEntity.ok("🔄 Service de synchronisation Keycloak actif");
    }
}
