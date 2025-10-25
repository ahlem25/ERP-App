package com.iss4u.erp.services.modules.achat.domain.common.dto.listeprix.request;

import com.iss4u.erp.services.modules.achat.domain.common.dto.listeprix.request.PrixArticleRequest;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListePrixRequest {
    private Long id;

    @Column(nullable = false)
    private String nomListeDePrix;

    @Column(nullable = false)
    private String devise;

    private boolean actif;

    private boolean achat;

    private boolean vente;

    private boolean prixIndependantUniteMesure;
    private List<PrixArticleRequest> prixArticles;


}