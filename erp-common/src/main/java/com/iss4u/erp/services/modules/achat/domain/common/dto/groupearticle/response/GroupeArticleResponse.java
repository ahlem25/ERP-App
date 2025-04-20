package com.iss4u.erp.services.modules.achat.domain.common.dto.groupearticle.response;

import com.iss4u.erp.services.modules.achat.domain.common.models.Article;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.iss4u.erp.services.modules.achat.domain.common.models.GroupeArticle;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupeArticleResponse {
    private Long id;
    private String nomDuGroupe;
    private Double tauxTvaParDefaut;
    private String imageDuGroupe;
    private GroupeArticle groupeParent;
    private List<Article> articles;


}