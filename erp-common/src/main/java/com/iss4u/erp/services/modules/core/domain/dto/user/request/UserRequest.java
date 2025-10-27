package com.iss4u.erp.services.modules.core.domain.dto.user.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    
    @NotBlank(message = "Le prénom est obligatoire")
    @Size(min = 2, max = 50, message = "Le prénom doit contenir entre 2 et 50 caractères")
    private String firstName;
    
    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 2, max = 50, message = "Le nom doit contenir entre 2 et 50 caractères")
    private String lastName;
    
    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit être valide")
    private String email;
    
    @NotBlank(message = "Le nom d'utilisateur est obligatoire")
    @Size(min = 3, max = 30, message = "Le nom d'utilisateur doit contenir entre 3 et 30 caractères")
    private String username;
    
    private String password;
    
    private Boolean enabled = true;
    
    private Boolean emailVerified = false;
    
    // Support both single role and list of roles
    private String role;
    
    private List<String> roles;
    
    private String phoneNumber;
    
    private String department;
    
    private String jobTitle;
    
    private String profilePicture;
    
    // Helper method to get roles list - supports both single role and list of roles
    public List<String> getRolesList() {
        if (roles != null && !roles.isEmpty()) {
            return roles;
        }
        if (role != null && !role.isEmpty()) {
            return List.of(role);
        }
        return List.of();
    }
}
