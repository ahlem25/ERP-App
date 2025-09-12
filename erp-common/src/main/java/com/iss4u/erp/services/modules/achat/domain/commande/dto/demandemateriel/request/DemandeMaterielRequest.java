package com.iss4u.erp.services.modules.achat.domain.commande.dto.demandemateriel.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.iss4u.erp.services.modules.achat.domain.common.models.Article;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DemandeMaterielRequest {

    private String serie;

    private String type;

    private LocalDate dateTransaction;

    private String requisPar;

    private String commentaire;
    private Article article;


}