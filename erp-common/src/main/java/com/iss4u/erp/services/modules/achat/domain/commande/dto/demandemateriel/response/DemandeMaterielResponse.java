package com.iss4u.erp.services.modules.achat.domain.commande.dto.demandemateriel.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.iss4u.erp.services.modules.achat.domain.common.models.Article;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DemandeMaterielResponse {
    private Long id;
    private String serie;

    private String type;

    private LocalDate dateTransaction;

    private String requisPar;

    private String commentaire;
    private Article article;


}