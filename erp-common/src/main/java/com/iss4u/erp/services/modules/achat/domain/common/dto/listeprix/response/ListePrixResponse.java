package com.iss4u.erp.services.modules.achat.domain.common.dto.listeprix.response;

import com.iss4u.erp.services.modules.achat.domain.common.models.PrixArticle;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListePrixResponse {
    private Long id;
    private String nom;
    private String devise;
    private Boolean actif;
    private LocalDate valideDu;
    private LocalDate valideJusquau;
    private Boolean pourAchat;
    private Boolean pourVente;
    private List<PrixArticle> prixArticles;


}