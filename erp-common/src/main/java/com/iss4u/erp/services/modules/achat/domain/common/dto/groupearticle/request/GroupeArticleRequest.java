package com.iss4u.erp.services.modules.achat.domain.common.dto.groupearticle.request;

import com.iss4u.erp.services.modules.achat.domain.common.models.Article;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.iss4u.erp.services.modules.achat.domain.common.models.GroupeArticle;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupeArticleRequest {
    private String nom;

    private boolean estGroupe;
    @ManyToOne
    private GroupeArticle groupeParent;

    @OneToMany(mappedBy = "groupeArticle")
    private List<Article> articles;


}