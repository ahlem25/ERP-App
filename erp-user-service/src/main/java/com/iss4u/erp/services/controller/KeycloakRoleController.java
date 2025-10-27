package com.iss4u.erp.services.controller;

import com.iss4u.erp.services.modules.core.domain.dto.keycloakrole.response.KeycloakRoleNamesResponse;
import com.iss4u.erp.services.modules.core.domain.dto.keycloakrole.response.KeycloakRoleResponse;
import com.iss4u.erp.services.modules.core.domain.dto.keycloakrole.response.KeycloakRolesListResponse;
import com.iss4u.erp.services.modules.core.domain.dto.keycloakrole.response.KeycloakRealmInfoResponse;
import com.iss4u.erp.services.service.KeycloakRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/keycloak")
public class KeycloakRoleController {

    private final KeycloakRoleService keycloakRoleService;

    @GetMapping("/roles")
    public ResponseEntity<KeycloakRolesListResponse> getRealmRoles() {
        log.info("Récupération des rôles du realm");
        KeycloakRolesListResponse response = keycloakRoleService.getAllRealmRoles();
        
        if (response.getSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/roles/names")
    public ResponseEntity<KeycloakRoleNamesResponse> getRoleNames() {
        log.info("Récupération des noms de rôles du realm");
        KeycloakRoleNamesResponse response = keycloakRoleService.getRoleNames();
        
        if (response.getSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/roles/{roleName}")
    public ResponseEntity<KeycloakRoleResponse> getRoleByName(@PathVariable String roleName) {
        log.info("Récupération du rôle '{}' du realm", roleName);
        
        try {
            KeycloakRoleResponse response = keycloakRoleService.getRoleByName(roleName);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Erreur lors de la récupération du rôle '{}': {}", roleName, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/realm-info")
    public ResponseEntity<KeycloakRealmInfoResponse> getRealmInfo() {
        log.info("Récupération des informations du realm");
        KeycloakRealmInfoResponse response = keycloakRoleService.getRealmInfo();
        
        if (response.getSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
