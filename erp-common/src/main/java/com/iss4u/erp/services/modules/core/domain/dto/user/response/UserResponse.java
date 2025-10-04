package com.iss4u.erp.services.modules.core.domain.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    
    private String id;
    
    private String firstName;
    
    private String lastName;
    
    private String fullName;
    
    private String email;
    
    private String username;
    
    private Boolean enabled;
    
    private Boolean emailVerified;
    
    private LocalDateTime createdTimestamp;
    
    private LocalDateTime lastLoginTimestamp;
    
    private List<String> roles;
    
    private String status; // "Actif" ou "Inactif"
    
    private String profilePicture;
    
    private String phoneNumber;
    
    private String department;
    
    private String jobTitle;
    
    // Méthodes utilitaires
    public String getStatusDisplay() {
        return enabled ? "Actif" : "Inactif";
    }
    
    public String getStatusClass() {
        return enabled ? "status-active" : "status-inactive";
    }
    
    public String getRolesDisplay() {
        if (roles == null || roles.isEmpty()) {
            return "Aucun rôle";
        }
        return String.join(", ", roles);
    }
    
    public String getInitials() {
        if (firstName == null || lastName == null) {
            return "??";
        }
        return (firstName.charAt(0) + "" + lastName.charAt(0)).toUpperCase();
    }
}
