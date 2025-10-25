package com.iss4u.erp.services.modules.core.domain.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    
    private String email;
    private String password;
    private Boolean rememberMe = false;
}
