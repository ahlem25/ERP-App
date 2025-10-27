package com.iss4u.erp.services.modules.core.domain.dto.keycloakrole.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KeycloakRoleNamesResponse {
    private Boolean success;
    private String realm;
    private Integer totalRoles;
    private List<String> roleNames;
    private String message;
}
