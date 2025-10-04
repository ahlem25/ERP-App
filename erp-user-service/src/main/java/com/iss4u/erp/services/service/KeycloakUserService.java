package com.iss4u.erp.services.service;

import com.iss4u.erp.services.modules.core.domain.dto.user.response.UserListResponse;
import com.iss4u.erp.services.modules.core.domain.dto.user.response.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class KeycloakUserService {
    
    @Autowired
    private Keycloak keycloak;
    
    @Value("${keycloak.realm:master}")
    private String realm;
    
    /**
     * Récupère tous les utilisateurs avec pagination
     */
    public UserListResponse getAllUsers(int page, int size, String search) {
        try {
            RealmResource realmResource = keycloak.realm(realm);
            UsersResource usersResource = realmResource.users();
            
            // Calculer l'offset pour la pagination
            int firstResult = page * size;
            
            // Récupérer les utilisateurs
            List<UserRepresentation> users = usersResource.search(search, firstResult, size);
            
            // Récupérer le nombre total d'utilisateurs
            int totalCount = usersResource.count();
            
            // Convertir en DTOs
            List<UserResponse> userResponses = users.stream()
                    .map(this::convertToUserResponse)
                    .collect(Collectors.toList());
            
            // Calculer les informations de pagination
            int totalPages = (int) Math.ceil((double) totalCount / size);
            
            UserListResponse response = new UserListResponse();
            response.setUsers(userResponses);
            response.setTotalElements(totalCount);
            response.setTotalPages(totalPages);
            response.setCurrentPage(page);
            response.setSize(size);
            response.setFirst(page == 0);
            response.setLast(page >= totalPages - 1);
            response.setTotalUnreadNotifications(0); // À implémenter si nécessaire
            
            log.info("Retrieved {} users from Keycloak (page {}, size {})", userResponses.size(), page, size);
            return response;
            
        } catch (Exception e) {
            log.error("Error retrieving users from Keycloak", e);
            throw new RuntimeException("Failed to retrieve users from Keycloak", e);
        }
    }
    
    /**
     * Récupère un utilisateur par ID
     */
    public UserResponse getUserById(String userId) {
        try {
            RealmResource realmResource = keycloak.realm(realm);
            UserResource userResource = realmResource.users().get(userId);
            UserRepresentation user = userResource.toRepresentation();
            
            return convertToUserResponse(user);
            
        } catch (Exception e) {
            log.error("Error retrieving user with ID: {}", userId, e);
            throw new RuntimeException("Failed to retrieve user with ID: " + userId, e);
        }
    }
    
    /**
     * Crée un nouvel utilisateur
     */
    public UserResponse createUser(com.iss4u.erp.services.modules.core.domain.dto.user.request.UserRequest userRequest) {
        try {
            RealmResource realmResource = keycloak.realm(realm);
            UsersResource usersResource = realmResource.users();
            
            UserRepresentation user = new UserRepresentation();
            user.setUsername(userRequest.getUsername());
            user.setEmail(userRequest.getEmail());
            user.setFirstName(userRequest.getFirstName());
            user.setLastName(userRequest.getLastName());
            user.setEnabled(userRequest.getEnabled());
            user.setEmailVerified(userRequest.getEmailVerified());
            
            // Créer l'utilisateur
            var response = usersResource.create(user);
            String userId = getUserIdFromLocation(response.getLocation());
            
            // Définir le mot de passe si fourni
            if (userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()) {
                UserResource userResource = usersResource.get(userId);
                userResource.resetPassword(createPasswordCredential(userRequest.getPassword()));
            }
            
            // Assigner les rôles si fournis
            if (userRequest.getRoles() != null && !userRequest.getRoles().isEmpty()) {
                assignRolesToUser(userId, userRequest.getRoles());
            }
            
            log.info("User created successfully with ID: {}", userId);
            return getUserById(userId);
            
        } catch (Exception e) {
            log.error("Error creating user", e);
            throw new RuntimeException("Failed to create user", e);
        }
    }
    
    /**
     * Met à jour un utilisateur
     */
    public UserResponse updateUser(String userId, com.iss4u.erp.services.modules.core.domain.dto.user.request.UserRequest userRequest) {
        try {
            RealmResource realmResource = keycloak.realm(realm);
            UserResource userResource = realmResource.users().get(userId);
            UserRepresentation user = userResource.toRepresentation();
            
            // Mettre à jour les propriétés
            user.setEmail(userRequest.getEmail());
            user.setFirstName(userRequest.getFirstName());
            user.setLastName(userRequest.getLastName());
            user.setEnabled(userRequest.getEnabled());
            user.setEmailVerified(userRequest.getEmailVerified());
            
            // Sauvegarder les modifications
            userResource.update(user);
            
            // Mettre à jour le mot de passe si fourni
            if (userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()) {
                userResource.resetPassword(createPasswordCredential(userRequest.getPassword()));
            }
            
            // Mettre à jour les rôles si fournis
            if (userRequest.getRoles() != null) {
                assignRolesToUser(userId, userRequest.getRoles());
            }
            
            log.info("User updated successfully with ID: {}", userId);
            return getUserById(userId);
            
        } catch (Exception e) {
            log.error("Error updating user with ID: {}", userId, e);
            throw new RuntimeException("Failed to update user with ID: " + userId, e);
        }
    }
    
    /**
     * Supprime un utilisateur
     */
    public void deleteUser(String userId) {
        try {
            RealmResource realmResource = keycloak.realm(realm);
            UsersResource usersResource = realmResource.users();
            usersResource.delete(userId);
            
            log.info("User deleted successfully with ID: {}", userId);
            
        } catch (Exception e) {
            log.error("Error deleting user with ID: {}", userId, e);
            throw new RuntimeException("Failed to delete user with ID: " + userId, e);
        }
    }
    
    /**
     * Convertit UserRepresentation en UserResponse
     */
    private UserResponse convertToUserResponse(UserRepresentation user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setFullName(user.getFirstName() + " " + user.getLastName());
        response.setEmail(user.getEmail());
        response.setUsername(user.getUsername());
        response.setEnabled(user.isEnabled());
        response.setEmailVerified(user.isEmailVerified());
        
        // Convertir les timestamps
        if (user.getCreatedTimestamp() != null) {
            response.setCreatedTimestamp(LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(user.getCreatedTimestamp()),
                    ZoneId.systemDefault()
            ));
        }
        
        // Récupérer les rôles de l'utilisateur
        try {
            List<String> roles = getUserRoles(user.getId());
            response.setRoles(roles);
        } catch (Exception e) {
            log.warn("Could not retrieve roles for user: {}", user.getUsername(), e);
            response.setRoles(new ArrayList<>());
        }
        
        // Récupérer les attributs personnalisés
        if (user.getAttributes() != null) {
            response.setPhoneNumber(getAttributeValue(user.getAttributes(), "phoneNumber"));
            response.setDepartment(getAttributeValue(user.getAttributes(), "department"));
            response.setJobTitle(getAttributeValue(user.getAttributes(), "jobTitle"));
            response.setProfilePicture(getAttributeValue(user.getAttributes(), "profilePicture"));
        }
        
        return response;
    }
    
    /**
     * Récupère les rôles d'un utilisateur
     */
    private List<String> getUserRoles(String userId) {
        try {
            RealmResource realmResource = keycloak.realm(realm);
            UserResource userResource = realmResource.users().get(userId);
            List<RoleRepresentation> roles = userResource.roles().realmLevel().listAll();
            
            return roles.stream()
                    .map(RoleRepresentation::getName)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.warn("Could not retrieve roles for user: {}", userId, e);
            return new ArrayList<>();
        }
    }
    
    /**
     * Assigne des rôles à un utilisateur
     */
    private void assignRolesToUser(String userId, List<String> roleNames) {
        try {
            RealmResource realmResource = keycloak.realm(realm);
            UserResource userResource = realmResource.users().get(userId);
            
            // Récupérer les rôles du realm
            List<RoleRepresentation> availableRoles = realmResource.roles().list();
            
            // Filtrer les rôles demandés
            List<RoleRepresentation> rolesToAssign = availableRoles.stream()
                    .filter(role -> roleNames.contains(role.getName()))
                    .collect(Collectors.toList());
            
            // Assigner les rôles
            userResource.roles().realmLevel().add(rolesToAssign);
            
            log.info("Assigned {} roles to user: {}", rolesToAssign.size(), userId);
            
        } catch (Exception e) {
            log.error("Error assigning roles to user: {}", userId, e);
            throw new RuntimeException("Failed to assign roles to user", e);
        }
    }
    
    /**
     * Crée un credential de mot de passe
     */
    private org.keycloak.representations.idm.CredentialRepresentation createPasswordCredential(String password) {
        org.keycloak.representations.idm.CredentialRepresentation credential = 
                new org.keycloak.representations.idm.CredentialRepresentation();
        credential.setType(org.keycloak.representations.idm.CredentialRepresentation.PASSWORD);
        credential.setValue(password);
        credential.setTemporary(false);
        return credential;
    }
    
    /**
     * Extrait l'ID utilisateur de l'URL de localisation
     */
    private String getUserIdFromLocation(java.net.URI location) {
        String path = location.getPath();
        return path.substring(path.lastIndexOf('/') + 1);
    }
    
    /**
     * Récupère une valeur d'attribut
     */
    private String getAttributeValue(java.util.Map<String, java.util.List<String>> attributes, String key) {
        if (attributes.containsKey(key) && !attributes.get(key).isEmpty()) {
            return attributes.get(key).get(0);
        }
        return null;
    }
}
