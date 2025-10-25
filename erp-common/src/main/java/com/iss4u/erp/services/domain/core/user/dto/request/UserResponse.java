package com.iss4u.erp.services.domain.core.user.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    public String id;
    public String username;
    public String email;
    public boolean enabled;
}
