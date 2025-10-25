package com.iss4u.erp.services.service;

import com.iss4u.erp.services.modules.core.domain.models.Utilisateur;
import com.iss4u.erp.services.modules.core.domain.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeycloakSyncService {

    private final UtilisateurRepository utilisateurRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${keycloak.auth-server-url:http://localhost:8081}")
    private String keycloakUrl;

    @Value("${keycloak.realm:erp-realm}")
    private String realm;

    @Value("${keycloak.admin.username:admin}")
    private String adminUsername;

    @Value("${keycloak.admin.password:admin123}")
    private String adminPassword;

    /**
     * Synchronise tous les utilisateurs de Keycloak vers la base de données ERP
     */
    public void syncAllUsers() {
        try {
            log.info("🔄 Début de la synchronisation des utilisateurs Keycloak...");
            
            // Obtenir le token d'administration
            String adminToken = getAdminToken();
            if (adminToken == null) {
                log.error("❌ Impossible d'obtenir le token d'administration Keycloak");
                return;
            }

            // Récupérer tous les utilisateurs de Keycloak
            List<Map<String, Object>> keycloakUsers = getKeycloakUsers(adminToken);
            log.info("📊 {} utilisateurs trouvés dans Keycloak", keycloakUsers.size());

            // Synchroniser chaque utilisateur
            int syncedCount = 0;
            for (Map<String, Object> keycloakUser : keycloakUsers) {
                if (syncUser(keycloakUser)) {
                    syncedCount++;
                }
            }

            log.info("✅ Synchronisation terminée: {} utilisateurs synchronisés", syncedCount);

        } catch (Exception e) {
            log.error("❌ Erreur lors de la synchronisation: {}", e.getMessage(), e);
        }
    }

    /**
     * Synchronise un utilisateur spécifique
     */
    public boolean syncUser(Map<String, Object> keycloakUser) {
        try {
            String email = (String) keycloakUser.get("email");
            String username = (String) keycloakUser.get("username");
            String firstName = (String) keycloakUser.get("firstName");
            String lastName = (String) keycloakUser.get("lastName");
            Boolean enabled = (Boolean) keycloakUser.get("enabled");

            if (email == null || !Boolean.TRUE.equals(enabled)) {
                log.warn("⚠️ Utilisateur ignoré: email={}, enabled={}", email, enabled);
                return false;
            }

            // Vérifier si l'utilisateur existe déjà
            Optional<Utilisateur> existingUser = utilisateurRepository.findByEmail(email);
            
            if (existingUser.isPresent()) {
                log.info("🔄 Mise à jour de l'utilisateur existant: {}", email);
                Utilisateur user = existingUser.get();
                updateUserFromKeycloak(user, keycloakUser);
                utilisateurRepository.save(user);
            } else {
                log.info("➕ Création d'un nouvel utilisateur: {}", email);
                Utilisateur newUser = createUserFromKeycloak(keycloakUser);
                utilisateurRepository.save(newUser);
            }

            return true;

        } catch (Exception e) {
            log.error("❌ Erreur lors de la synchronisation de l'utilisateur {}: {}", 
                     keycloakUser.get("email"), e.getMessage());
            return false;
        }
    }

    /**
     * Obtient le token d'administration Keycloak
     */
    private String getAdminToken() {
        try {
            String tokenUrl = keycloakUrl + "/realms/master/protocol/openid-connect/token";
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            
            String body = String.format(
                "username=%s&password=%s&grant_type=password&client_id=admin-cli",
                adminUsername, adminPassword
            );
            
            HttpEntity<String> request = new HttpEntity<>(body, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, request, Map.class);
            
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return (String) response.getBody().get("access_token");
            }
            
        } catch (Exception e) {
            log.error("❌ Erreur lors de l'obtention du token admin: {}", e.getMessage());
        }
        
        return null;
    }

    /**
     * Récupère tous les utilisateurs de Keycloak
     */
    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> getKeycloakUsers(String adminToken) {
        try {
            String usersUrl = keycloakUrl + "/admin/realms/" + realm + "/users";
            
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(adminToken);
            
            HttpEntity<String> request = new HttpEntity<>(headers);
            ResponseEntity<List> response = restTemplate.exchange(
                usersUrl, HttpMethod.GET, request, List.class
            );
            
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return (List<Map<String, Object>>) response.getBody();
            }
            
        } catch (Exception e) {
            log.error("❌ Erreur lors de la récupération des utilisateurs Keycloak: {}", e.getMessage());
        }
        
        return new ArrayList<>();
    }

    /**
     * Crée un nouvel utilisateur à partir des données Keycloak
     */
    private Utilisateur createUserFromKeycloak(Map<String, Object> keycloakUser) {
        Utilisateur user = new Utilisateur();
        user.setEmail((String) keycloakUser.get("email"));
        user.setNomUtilisateur((String) keycloakUser.get("username"));
        user.setPassword("keycloak_user"); // Mot de passe géré par Keycloak
        user.setPrenom((String) keycloakUser.get("firstName"));
        user.setNomFamille((String) keycloakUser.get("lastName"));
        user.setActif(Boolean.TRUE.equals(keycloakUser.get("enabled")));
        user.setEmailVerifie(true);
        user.setLangue("fr");
        user.setFuseauHoraire("Europe/Paris");
        user.setDerniereMaj(new Date());
        
        return user;
    }

    /**
     * Met à jour un utilisateur existant avec les données Keycloak
     */
    private void updateUserFromKeycloak(Utilisateur user, Map<String, Object> keycloakUser) {
        user.setPrenom((String) keycloakUser.get("firstName"));
        user.setNomFamille((String) keycloakUser.get("lastName"));
        user.setActif(Boolean.TRUE.equals(keycloakUser.get("enabled")));
        user.setDerniereMaj(new Date());
    }
}
