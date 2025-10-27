package com.iss4u.erp.services.service;

import com.iss4u.erp.services.modules.core.domain.dto.user.request.PasswordChangeRequest;
import com.iss4u.erp.services.modules.core.domain.dto.user.request.ProfileUpdateRequest;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class KeycloakProfileService {
    
    @Autowired
    private Keycloak keycloak;
    
    @Value("${keycloak.realm:master}")
    private String realm;
    
    /**
     * Met à jour le profil d'un utilisateur
     */
    public UserResponse updateProfile(String userId, ProfileUpdateRequest request) {
        try {
            RealmResource realmResource = keycloak.realm(realm);
            UserResource userResource = realmResource.users().get(userId);
            UserRepresentation user = userResource.toRepresentation();
            
            // Vérifier si l'email change et s'il existe déjà pour un autre utilisateur
            if (request.getEmail() != null && !request.getEmail().isEmpty() 
                    && !request.getEmail().equals(user.getEmail())) {
                UsersResource usersResource = realmResource.users();
                List<UserRepresentation> existingUsers = usersResource.searchByEmail(request.getEmail(), true);
                if (!existingUsers.isEmpty() && !existingUsers.get(0).getId().equals(userId)) {
                    throw new UserAlreadyExistsException("Un utilisateur avec l'email '" + request.getEmail() + "' existe déjà");
                }
            }
            
            // Vérifier si le username change et s'il existe déjà pour un autre utilisateur
            if (request.getUsername() != null && !request.getUsername().isEmpty() 
                    && !request.getUsername().equals(user.getUsername())) {
                UsersResource usersResource = realmResource.users();
                List<UserRepresentation> existingByUsername = usersResource.searchByUsername(request.getUsername(), true);
                if (!existingByUsername.isEmpty() && !existingByUsername.get(0).getId().equals(userId)) {
                    throw new UserAlreadyExistsException("Un utilisateur avec le nom d'utilisateur '" + request.getUsername() + "' existe déjà");
                }
            }
            
            // Mettre à jour les propriétés si elles sont fournies
            if (request.getFirstName() != null && !request.getFirstName().isEmpty()) {
                user.setFirstName(request.getFirstName());
            }
            if (request.getLastName() != null && !request.getLastName().isEmpty()) {
                user.setLastName(request.getLastName());
            }
            if (request.getEmail() != null && !request.getEmail().isEmpty()) {
                user.setEmail(request.getEmail());
            }
            if (request.getUsername() != null && !request.getUsername().isEmpty()) {
                user.setUsername(request.getUsername());
            }
            
            // Mettre à jour les attributs personnalisés
            Map<String, List<String>> attributes = user.getAttributes() != null 
                    ? new HashMap<>(user.getAttributes()) 
                    : new HashMap<>();
            
            if (request.getPhoneNumber() != null && !request.getPhoneNumber().isEmpty()) {
                attributes.put("phoneNumber", List.of(request.getPhoneNumber()));
            }
            if (request.getDepartment() != null && !request.getDepartment().isEmpty()) {
                attributes.put("department", List.of(request.getDepartment()));
            }
            if (request.getJobTitle() != null && !request.getJobTitle().isEmpty()) {
                attributes.put("jobTitle", List.of(request.getJobTitle()));
            }
            if (request.getProfilePicture() != null && !request.getProfilePicture().isEmpty()) {
                attributes.put("profilePicture", List.of(request.getProfilePicture()));
            }
            user.setAttributes(attributes);
            
            // Sauvegarder les modifications
            userResource.update(user);
            
            log.info("Profile updated successfully for user ID: {}", userId);
            return getUserById(userId);
            
        } catch (UserAlreadyExistsException e) {
            log.error("User already exists during profile update", e);
            throw e;
        } catch (Exception e) {
            log.error("Error updating profile for user ID: {}", userId, e);
            throw new RuntimeException("Failed to update profile", e);
        }
    }
    
    /**
     * Change le mot de passe d'un utilisateur
     */
    public void changePassword(String userId, PasswordChangeRequest request) {
        try {
            RealmResource realmResource = keycloak.realm(realm);
            UserResource userResource = realmResource.users().get(userId);
            
            // Note: Keycloak ne permet pas de vérifier l'ancien mot de passe via l'admin client
            // Il faudrait utiliser l'API de vérification de credential ou le flux d'authentification
            
            // Mettre à jour le mot de passe
            userResource.resetPassword(createPasswordCredential(request.getNewPassword()));
            
            log.info("Password changed successfully for user ID: {}", userId);
            
        } catch (Exception e) {
            log.error("Error changing password for user ID: {}", userId, e);
            throw new RuntimeException("Failed to change password", e);
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
            
            UserResponse response = new UserResponse();
            response.setId(user.getId());
            response.setFirstName(user.getFirstName());
            response.setLastName(user.getLastName());
            response.setFullName(user.getFirstName() + " " + user.getLastName());
            response.setEmail(user.getEmail());
            response.setUsername(user.getUsername());
            response.setEnabled(user.isEnabled());
            response.setEmailVerified(user.isEmailVerified());
            
            // Récupérer les attributs personnalisés
            if (user.getAttributes() != null) {
                response.setPhoneNumber(getAttributeValue(user.getAttributes(), "phoneNumber"));
                response.setDepartment(getAttributeValue(user.getAttributes(), "department"));
                response.setJobTitle(getAttributeValue(user.getAttributes(), "jobTitle"));
                response.setProfilePicture(getAttributeValue(user.getAttributes(), "profilePicture"));
            }
            
            // Récupérer les rôles
            try {
                List<RoleRepresentation> roles = userResource.roles().realmLevel().listAll();
                List<String> roleNames = roles.stream()
                        .map(RoleRepresentation::getName)
                        .collect(Collectors.toList());
                response.setRoles(roleNames);
            } catch (Exception e) {
                log.warn("Could not retrieve roles for user: {}", user.getUsername(), e);
                response.setRoles(List.of());
            }
            
            return response;
            
        } catch (Exception e) {
            log.error("Error retrieving user with ID: {}", userId, e);
            throw new RuntimeException("Failed to retrieve user", e);
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
     * Récupère une valeur d'attribut
     */
    private String getAttributeValue(Map<String, List<String>> attributes, String key) {
        if (attributes.containsKey(key) && !attributes.get(key).isEmpty()) {
            return attributes.get(key).get(0);
        }
        return null;
    }
}

