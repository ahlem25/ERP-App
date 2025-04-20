package com.iss4u.erp.services.domain.core.user.dto.reponse;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    public String username;
    public String email;
    public String password;
}
