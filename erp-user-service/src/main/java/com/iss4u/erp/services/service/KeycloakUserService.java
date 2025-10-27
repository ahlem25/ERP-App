package com.iss4u.erp.services.service;

import com.iss4u.erp.services.modules.core.domain.dto.user.response.UserListResponse;
import com.iss4u.erp.services.modules.core.domain.dto.user.response.UserResponse;
import com.iss4u.erp.services.modules.core.domain.exception.UserAlreadyExistsException;
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
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class KeycloakUserService {
    
    @Autowired
    private Keycloak keycloak;
    
    @Value("${keycloak.realm:master}")
    private String realm;
    
    @Value("${PRODUCT_SERVICE_URL:http://localhost:8051}")
    private String productServiceUrl;
    
    private final RestTemplate restTemplate = new RestTemplate();
    
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
            
            // Vérifier les conflits AVANT de créer l'utilisateur
            List<UserRepresentation> existingByEmail = usersResource.searchByEmail(userRequest.getEmail(), true);
            List<UserRepresentation> existingByUsername = usersResource.searchByUsername(userRequest.getUsername(), true);
            
            log.info("searchByEmail found {} users, searchByUsername found {} users", 
                    existingByEmail.size(), existingByUsername.size());
            
            if (!existingByEmail.isEmpty() && !existingByUsername.isEmpty()) {
                throw new UserAlreadyExistsException("Un utilisateur avec l'email '" + userRequest.getEmail() + "' et le nom d'utilisateur '" + userRequest.getUsername() + "' existe déjà");
            } else if (!existingByEmail.isEmpty()) {
                throw new UserAlreadyExistsException("Un utilisateur avec l'email '" + userRequest.getEmail() + "' existe déjà");
            } else if (!existingByUsername.isEmpty()) {
                throw new UserAlreadyExistsException("Un utilisateur avec le nom d'utilisateur '" + userRequest.getUsername() + "' existe déjà");
            }
            
            UserRepresentation user = new UserRepresentation();
            user.setUsername(userRequest.getUsername());
            user.setEmail(userRequest.getEmail());
            user.setFirstName(userRequest.getFirstName());
            user.setLastName(userRequest.getLastName());
            user.setEnabled(userRequest.getEnabled());
            user.setEmailVerified(userRequest.getEmailVerified());
            
            // Ajouter les attributs personnalisés
            java.util.Map<String, java.util.List<String>> attributes = new java.util.HashMap<>();
            if (userRequest.getPhoneNumber() != null && !userRequest.getPhoneNumber().isEmpty()) {
                attributes.put("phoneNumber", java.util.Arrays.asList(userRequest.getPhoneNumber()));
            }
            if (userRequest.getDepartment() != null && !userRequest.getDepartment().isEmpty()) {
                attributes.put("department", java.util.Arrays.asList(userRequest.getDepartment()));
            }
            if (userRequest.getJobTitle() != null && !userRequest.getJobTitle().isEmpty()) {
                attributes.put("jobTitle", java.util.Arrays.asList(userRequest.getJobTitle()));
            }
            if (userRequest.getProfilePicture() != null && !userRequest.getProfilePicture().isEmpty()) {
                attributes.put("profilePicture", java.util.Arrays.asList(userRequest.getProfilePicture()));
            }
            if (!attributes.isEmpty()) {
                user.setAttributes(attributes);
            }
            
            // Créer l'utilisateur
            String userId;
            try (var response = usersResource.create(user)) {
                int statusCode = response.getStatus();
                
                // Vérifier si la création a échoué
                if (statusCode == 409 || statusCode == 400) {
                    log.error("Failed to create user, status: {}. Email: {}, Username: {}", 
                             statusCode, userRequest.getEmail(), userRequest.getUsername());
                    
                    // Lire le message d'erreur exact de Keycloak
                    String errorMessage = "Erreur inconnue";
                    try {
                        String errorEntity = response.readEntity(String.class);
                        log.error("Keycloak error response: {}", errorEntity);
                        errorMessage = errorEntity;
                    } catch (Exception e) {
                        log.error("Could not read error response", e);
                    }
                    
                    // Comme on a déjà vérifié les conflits, si on arrive ici c'est une erreur de validation Keycloak
                    log.error("Erreur Keycloak après vérifications de conflit. Message: {}", errorMessage);
                    throw new RuntimeException("Erreur lors de la création de l'utilisateur: " + errorMessage);
                }
                
                userId = getUserIdFromLocation(response.getLocation());
            }
            
            // Définir le mot de passe si fourni
            if (userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()) {
                UserResource userResource = usersResource.get(userId);
                userResource.resetPassword(createPasswordCredential(userRequest.getPassword()));
            }
            
            // Assigner les rôles si fournis (supporte à la fois un seul rôle et une liste de rôles)
            List<String> rolesToAssign = userRequest.getRolesList();
            if (!rolesToAssign.isEmpty()) {
                assignRolesToUser(userId, rolesToAssign);
            }
            
            log.info("User created successfully with ID: {}", userId);
            return getUserById(userId);
            
        } catch (UserAlreadyExistsException e) {
            log.error("User already exists", e);
            throw e;
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
            UsersResource usersResource = realmResource.users();
            UserResource userResource = usersResource.get(userId);
            UserRepresentation user = userResource.toRepresentation();
            
            // Vérifier si l'email change et s'il existe déjà pour un autre utilisateur
            if (!user.getEmail().equals(userRequest.getEmail())) {
                List<UserRepresentation> existingUsers = usersResource.searchByEmail(userRequest.getEmail(), true);
                if (!existingUsers.isEmpty() && !existingUsers.get(0).getId().equals(userId)) {
                    log.warn("User with email {} already exists (different user)", userRequest.getEmail());
                    throw new UserAlreadyExistsException("Un utilisateur avec l'email '" + userRequest.getEmail() + "' existe déjà");
                }
            }
            
            // Vérifier si le username change et s'il existe déjà pour un autre utilisateur
            if (!user.getUsername().equals(userRequest.getUsername())) {
                List<UserRepresentation> existingByUsername = usersResource.searchByUsername(userRequest.getUsername(), true);
                if (!existingByUsername.isEmpty() && !existingByUsername.get(0).getId().equals(userId)) {
                    log.warn("User with username {} already exists (different user)", userRequest.getUsername());
                    throw new UserAlreadyExistsException("Un utilisateur avec le nom d'utilisateur '" + userRequest.getUsername() + "' existe déjà");
                }
            }
            
            // Mettre à jour les propriétés
            user.setEmail(userRequest.getEmail());
            user.setUsername(userRequest.getUsername());
            user.setFirstName(userRequest.getFirstName());
            user.setLastName(userRequest.getLastName());
            user.setEnabled(userRequest.getEnabled());
            user.setEmailVerified(userRequest.getEmailVerified());
            
            // Mettre à jour les attributs personnalisés
            java.util.Map<String, java.util.List<String>> attributes = user.getAttributes() != null 
                    ? new java.util.HashMap<>(user.getAttributes()) 
                    : new java.util.HashMap<>();
            
            if (userRequest.getPhoneNumber() != null && !userRequest.getPhoneNumber().isEmpty()) {
                attributes.put("phoneNumber", java.util.Arrays.asList(userRequest.getPhoneNumber()));
            }
            if (userRequest.getDepartment() != null && !userRequest.getDepartment().isEmpty()) {
                attributes.put("department", java.util.Arrays.asList(userRequest.getDepartment()));
            }
            if (userRequest.getJobTitle() != null && !userRequest.getJobTitle().isEmpty()) {
                attributes.put("jobTitle", java.util.Arrays.asList(userRequest.getJobTitle()));
            }
            if (userRequest.getProfilePicture() != null && !userRequest.getProfilePicture().isEmpty()) {
                attributes.put("profilePicture", java.util.Arrays.asList(userRequest.getProfilePicture()));
            }
            user.setAttributes(attributes);
            
            // Sauvegarder les modifications
            userResource.update(user);
            
            // Mettre à jour le mot de passe si fourni
            if (userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()) {
                userResource.resetPassword(createPasswordCredential(userRequest.getPassword()));
            }
            
            // Mettre à jour les rôles si fournis (supporte à la fois un seul rôle et une liste de rôles)
            List<String> newRoles = userRequest.getRolesList();
            if (!newRoles.isEmpty()) {
                updateUserRoles(userId, newRoles);
            }
            
            log.info("User updated successfully with ID: {}", userId);
            return getUserById(userId);
            
        } catch (UserAlreadyExistsException e) {
            log.error("User already exists during update", e);
            throw e;
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
            
            // Récupérer l'utilisateur avant de le supprimer pour nettoyer son image de profil
            try {
                UserResource userResource = usersResource.get(userId);
                UserRepresentation user = userResource.toRepresentation();
                
                // Récupérer le profilePicture si présent
                String profilePicture = getAttributeValue(user.getAttributes(), "profilePicture");
                
                // Supprimer l'image si elle existe
                if (profilePicture != null && !profilePicture.isEmpty()) {
                    deleteProfilePicture(profilePicture);
                }
            } catch (Exception e) {
                log.warn("Could not retrieve user details before deletion: {}", userId, e);
                // Continue with deletion even if we can't clean up the picture
            }
            
            // Supprimer l'utilisateur
            usersResource.delete(userId);
            
            log.info("User deleted successfully with ID: {}", userId);
            
        } catch (Exception e) {
            log.error("Error deleting user with ID: {}", userId, e);
            throw new RuntimeException("Failed to delete user with ID: " + userId, e);
        }
    }
    
    /**
     * Supprime l'image de profil via le product service
     */
    private void deleteProfilePicture(String profilePicture) {
        try {
            log.info("Attempting to delete profile picture: {}", profilePicture);
            
            // Extraire le fileName du chemin complet
            String fileName = extractFileNameFromPath(profilePicture);
            log.info("Extracted fileName from profile picture: {}", fileName);
            
            // Construire l'URL pour supprimer le fichier (path parameter avec juste le fileName)
            String deleteUrl = productServiceUrl + "/api/v1/upload/" + 
                               java.net.URLEncoder.encode(fileName, "UTF-8");
            
            // Appeler le product service pour supprimer le fichier
            restTemplate.delete(deleteUrl);
            
            log.info("Profile picture deleted successfully: {}", fileName);
            
        } catch (Exception e) {
            log.error("Failed to delete profile picture: {}", profilePicture, e);
            // Ne pas lancer d'exception pour ne pas bloquer la suppression de l'utilisateur
        }
    }
    
    /**
     * Extrait le nom du fichier d'un chemin complet (URL S3 ou chemin local)
     */
    private String extractFileNameFromPath(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            return filePath;
        }
        
        // Extraire le nom du fichier (dernier élément après le dernier /)
        String[] parts = filePath.replace("\\", "/").split("/");
        return parts[parts.length - 1];
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
        
        // Récupérer le dernier login depuis les attributs personnalisés ou les sessions
        if (user.getAttributes() != null) {
            String lastLoginStr = getAttributeValue(user.getAttributes(), "lastLoginTimestamp");
            if (lastLoginStr != null && !lastLoginStr.isEmpty()) {
                try {
                    long lastLogin = Long.parseLong(lastLoginStr);
                    response.setLastLoginTimestamp(LocalDateTime.ofInstant(
                            Instant.ofEpochMilli(lastLogin),
                            ZoneId.systemDefault()
                    ));
                } catch (Exception e) {
                    log.warn("Could not parse lastLoginTimestamp: {}", lastLoginStr, e);
                    response.setLastLoginTimestamp(null);
                }
            } else {
                // Essayer de récupérer depuis les sessions utilisateur
                response.setLastLoginTimestamp(getLastLoginFromSessions(user.getId()));
            }
        } else {
            response.setLastLoginTimestamp(null);
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
     * Récupère la date du dernier login depuis les sessions utilisateur
     */
    private LocalDateTime getLastLoginFromSessions(String userId) {
        try {
            RealmResource realmResource = keycloak.realm(realm);
            UserResource userResource = realmResource.users().get(userId);
            
            // Récupérer les sessions utilisateur
            List<org.keycloak.representations.idm.UserSessionRepresentation> sessions = userResource.getUserSessions();
            
            if (sessions != null && !sessions.isEmpty()) {
                // Trouver la session la plus récente basée sur la date de début (start)
                // getStart() retourne un long primitif, on le convertit en Long pour le stream
                Long maxStartTime = sessions.stream()
                        .mapToLong(session -> session.getStart())
                        .boxed()
                        .max(Long::compareTo)
                        .orElse(null);
                
                if (maxStartTime != null && maxStartTime > 0) {
                    return LocalDateTime.ofInstant(
                            Instant.ofEpochSecond(maxStartTime),
                            ZoneId.systemDefault()
                    );
                }
            }
            
            return null;
        } catch (Exception e) {
            log.debug("Could not retrieve last login from sessions for user: {}", userId, e);
            return null;
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
     * Met à jour les rôles d'un utilisateur en ajoutant les nouveaux et retirant les anciens
     */
    private void updateUserRoles(String userId, List<String> newRoleNames) {
        try {
            RealmResource realmResource = keycloak.realm(realm);
            UserResource userResource = realmResource.users().get(userId);
            
            // Récupérer les rôles actuels de l'utilisateur
            List<RoleRepresentation> currentRoles = userResource.roles().realmLevel().listAll();
            List<String> currentRoleNames = currentRoles.stream()
                    .map(RoleRepresentation::getName)
                    .collect(Collectors.toList());
            
            log.info("Current roles: {}, New roles: {}", currentRoleNames, newRoleNames);
            
            // Récupérer tous les rôles disponibles
            List<RoleRepresentation> availableRoles = realmResource.roles().list();
            
            // Trouver les rôles à ajouter (dans newRoles mais pas dans currentRoles)
            List<String> rolesToAdd = newRoleNames.stream()
                    .filter(roleName -> !currentRoleNames.contains(roleName))
                    .collect(Collectors.toList());
            
            // Trouver les rôles à retirer (dans currentRoles mais pas dans newRoles)
            List<String> rolesToRemove = currentRoleNames.stream()
                    .filter(roleName -> !newRoleNames.contains(roleName))
                    .collect(Collectors.toList());
            
            log.info("Roles to add: {}, Roles to remove: {}", rolesToAdd, rolesToRemove);
            
            // Ajouter les nouveaux rôles
            if (!rolesToAdd.isEmpty()) {
                List<RoleRepresentation> rolesToAssign = availableRoles.stream()
                        .filter(role -> rolesToAdd.contains(role.getName()))
                        .collect(Collectors.toList());
                userResource.roles().realmLevel().add(rolesToAssign);
                log.info("Added {} roles to user: {}", rolesToAssign.size(), userId);
            }
            
            // Retirer les anciens rôles
            if (!rolesToRemove.isEmpty()) {
                List<RoleRepresentation> rolesToUnassign = availableRoles.stream()
                        .filter(role -> rolesToRemove.contains(role.getName()))
                        .collect(Collectors.toList());
                userResource.roles().realmLevel().remove(rolesToUnassign);
                log.info("Removed {} roles from user: {}", rolesToUnassign.size(), userId);
            }
            
        } catch (Exception e) {
            log.error("Error updating roles for user: {}", userId, e);
            throw new RuntimeException("Failed to update roles for user", e);
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
