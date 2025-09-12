package com.iss4u.erp.services.modules.core.domain.dto.document.response;

import com.iss4u.erp.services.modules.core.domain.models.Activite;
import com.iss4u.erp.services.modules.core.domain.models.Commentaire;
import com.iss4u.erp.services.modules.core.domain.models.Journal;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentResponse {
    private Long id;
    private String reference;
    private String typeDocument;
    private Boolean estStandard;
    private Boolean actif;
    private Date dateCreation;
    private Date dateDerniereModif;
    private List<Journal> journaux;
    private List<Activite> activites;
    private List<Commentaire> commentaires;


}