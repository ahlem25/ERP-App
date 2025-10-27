package com.iss4u.erp.services.service;

import com.iss4u.erp.services.modules.core.domain.dto.keycloakrole.response.KeycloakRoleNamesResponse;
import com.iss4u.erp.services.modules.core.domain.dto.keycloakrole.response.KeycloakRoleResponse;
import com.iss4u.erp.services.modules.core.domain.dto.keycloakrole.response.KeycloakRolesListResponse;
import com.iss4u.erp.services.modules.core.domain.dto.keycloakrole.response.KeycloakRealmInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeycloakRoleService {

    private final Keycloak keycloak;

    @Value("${keycloak.realm:erp-realm}")
    private String realmName;

    public KeycloakRolesListResponse getAllRealmRoles() {
        try {
            log.info("Récupération de tous les rôles du realm: {}", realmName);
            
            List<RoleRepresentation> roles = keycloak.realm(realmName).roles().list();
            
            List<KeycloakRoleResponse> roleResponses = roles.stream()
                .map(this::mapToKeycloakRoleResponse)
                .collect(Collectors.toList());
            
            log.info("Récupération réussie de {} rôles du realm {}", roles.size(), realmName);
            
            return KeycloakRolesListResponse.builder()
                .success(true)
                .realm(realmName)
                .totalRoles(roles.size())
                .roles(roleResponses)
                .build();
                
        } catch (Exception e) {
            log.error("Erreur lors de la récupération des rôles du realm {}: {}", realmName, e.getMessage());
            
            return KeycloakRolesListResponse.builder()
                .success(false)
                .realm(realmName)
                .totalRoles(0)
                .roles(List.of())
                .message("Erreur lors de la récupération des rôles: " + e.getMessage())
                .build();
        }
    }

    public KeycloakRoleNamesResponse getRoleNames() {
        try {
            log.info("Récupération des noms de rôles du realm: {}", realmName);
            
            List<RoleRepresentation> roles = keycloak.realm(realmName).roles().list();
            
            List<String> roleNames = roles.stream()
                .map(RoleRepresentation::getName)
                .collect(Collectors.toList());
            
            log.info("Récupération réussie de {} noms de rôles du realm {}", roles.size(), realmName);
            
            return KeycloakRoleNamesResponse.builder()
                .success(true)
                .realm(realmName)
                .totalRoles(roles.size())
                .roleNames(roleNames)
                .build();
                
        } catch (Exception e) {
            log.error("Erreur lors de la récupération des noms de rôles du realm {}: {}", realmName, e.getMessage());
            
            return KeycloakRoleNamesResponse.builder()
                .success(false)
                .realm(realmName)
                .totalRoles(0)
                .roleNames(List.of())
                .message("Erreur lors de la récupération des noms de rôles: " + e.getMessage())
                .build();
        }
    }

    public KeycloakRoleResponse getRoleByName(String roleName) {
        try {
            log.info("Récupération du rôle '{}' du realm: {}", roleName, realmName);
            
            RoleRepresentation role = keycloak.realm(realmName).roles().get(roleName).toRepresentation();
            
            log.info("Récupération réussie du rôle '{}' du realm {}", roleName, realmName);
            
            return mapToKeycloakRoleResponse(role);
            
        } catch (Exception e) {
            log.error("Erreur lors de la récupération du rôle '{}' du realm {}: {}", roleName, realmName, e.getMessage());
            throw new RuntimeException("Rôle non trouvé ou erreur: " + e.getMessage(), e);
        }
    }

    public KeycloakRealmInfoResponse getRealmInfo() {
        try {
            log.info("Récupération des informations du realm: {}", realmName);
            
            var realm = keycloak.realm(realmName).toRepresentation();
            List<RoleRepresentation> roles = keycloak.realm(realmName).roles().list();
            
            log.info("Récupération réussie des informations du realm {}", realmName);
            
            return KeycloakRealmInfoResponse.builder()
                .success(true)
                .realm(realmName)
                .realmId(realm.getId())
                .displayName(realm.getDisplayName())
                .enabled(realm.isEnabled())
                .totalRoles(roles.size())
                .build();
                
        } catch (Exception e) {
            log.error("Erreur lors de la récupération des informations du realm {}: {}", realmName, e.getMessage());
            
            return KeycloakRealmInfoResponse.builder()
                .success(false)
                .realm(realmName)
                .message("Erreur lors de la récupération des informations du realm: " + e.getMessage())
                .build();
        }
    }

    private KeycloakRoleResponse mapToKeycloakRoleResponse(RoleRepresentation role) {
        return KeycloakRoleResponse.builder()
            .id(role.getId())
            .name(role.getName())
            .description(role.getDescription())
            .composite(role.isComposite())
            .clientRole(role.getClientRole() != null ? role.getClientRole() : false)
            .containerId(role.getContainerId())
            .build();
    }
}
