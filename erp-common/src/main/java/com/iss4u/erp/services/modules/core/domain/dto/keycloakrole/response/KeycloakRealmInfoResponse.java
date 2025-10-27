package com.iss4u.erp.services.modules.core.domain.dto.keycloakrole.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KeycloakRealmInfoResponse {
    private Boolean success;
    private String realm;
    private String realmId;
    private String displayName;
    private Boolean enabled;
    private Integer totalRoles;
    private String message;
}
