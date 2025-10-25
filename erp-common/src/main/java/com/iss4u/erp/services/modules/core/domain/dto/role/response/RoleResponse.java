package com.iss4u.erp.services.modules.core.domain.dto.role.response;

import com.iss4u.erp.services.modules.core.domain.models.Autorisation;
import com.iss4u.erp.services.modules.core.domain.models.ModuleProfile;
import com.iss4u.erp.services.modules.core.domain.models.ProfilRole;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponse {
    private Long id;
    private String nomRole;
    private String pageAccueil;
    private String route;
    private String domaineRestreint;
    private Boolean desactive;
    private Boolean isCustom;
    private Boolean accesBureau;
    private Boolean authDoubleFacteur;
    private List<ProfilRole> profils;
    private List<ModuleProfile> modules;
    private List<Autorisation> autorisations;


}