package com.iss4u.erp.services.modules.core.domain.dto.auth;

import com.iss4u.erp.services.modules.core.domain.models.Utilisateur;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    
    private Utilisateur user;
    private String token;
    private String refreshToken;
    private String tokenType = "Bearer";
    private Long expiresIn; // en secondes
    private LocalDateTime expiresAt;
    private List<String> roles;
    private List<String> permissions;
    private String message;
    
    public AuthResponse(Utilisateur user, String token, String refreshToken, Long expiresIn) {
        this.user = user;
        this.token = token;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
        this.expiresAt = LocalDateTime.now().plusSeconds(expiresIn);
    }
}
