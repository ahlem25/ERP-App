package com.iss4u.erp.services.modules.core.domain.dto.utilisateur.request;

import com.iss4u.erp.services.modules.core.domain.models.Autorisation;
import com.iss4u.erp.services.modules.core.domain.models.Commentaire;
import com.iss4u.erp.services.modules.core.domain.models.Document;
import com.iss4u.erp.services.modules.core.domain.models.Role;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurRequest {
    private String email;
    private String password;
    private String prenom;
    private String nomFamille;
    private Boolean actif;
    private Date derniereMaj;
    private List<Role> roles;
    private List<Autorisation> autorisations;
    private List<Document> documents;
    private List<Commentaire> commentaires;
}