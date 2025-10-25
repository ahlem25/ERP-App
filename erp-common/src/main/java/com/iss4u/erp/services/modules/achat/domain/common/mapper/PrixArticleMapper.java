package com.iss4u.erp.services.modules.achat.domain.common.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import com.iss4u.erp.services.modules.achat.domain.common.dto.prixarticle.request.PrixArticleRequest;
import com.iss4u.erp.services.modules.achat.domain.common.dto.prixarticle.response.PrixArticleResponse;
import com.iss4u.erp.services.modules.achat.domain.common.models.PrixArticle;

@Mapper(componentModel = "spring")
public interface PrixArticleMapper {
    
    @Mapping(target = "articleId", source = "article.id")
    @Mapping(target = "articleNom", source = "article.nomArticle")
    @Mapping(target = "articleCode", source = "article.codeArticle")
    @Mapping(target = "listeDePrixId", source = "listeDePrix.id")
    @Mapping(target = "listeDePrixNom", source = "listeDePrix.nomListeDePrix")
    @Mapping(target = "fournisseurId", ignore = true)
    @Mapping(target = "fournisseurNom", ignore = true)
    PrixArticleResponse toResponse(PrixArticle entity);

    @Mapping(target = "article", ignore = true)
    @Mapping(target = "listeDePrix", ignore = true)
    @Mapping(target = "fournisseur", ignore = true)
    PrixArticle toEntity(PrixArticleRequest dto);

    @Mapping(target = "article", ignore = true)
    @Mapping(target = "listeDePrix", ignore = true)
    @Mapping(target = "fournisseur", ignore = true)
    void updateFromDto(PrixArticleRequest dto, @MappingTarget PrixArticle entity);
}