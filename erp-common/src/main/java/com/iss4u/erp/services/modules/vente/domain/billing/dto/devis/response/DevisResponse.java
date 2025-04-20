package com.iss4u.erp.services.modules.vente.domain.billing.dto.devis.response;

import com.iss4u.erp.services.modules.achat.domain.common.models.Article;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;

import com.iss4u.erp.services.modules.vente.domain.client.models.Client;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DevisResponse {
    private Long id;
    private String numero;
    private Date validite;
    private Float montantTotal;
    private Client client;
    private List<Article> articles;


}