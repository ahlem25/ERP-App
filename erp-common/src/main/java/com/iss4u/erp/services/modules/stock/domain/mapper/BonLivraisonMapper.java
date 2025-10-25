package com.iss4u.erp.services.modules.stock.domain.mapper;

import com.iss4u.erp.services.modules.stock.domain.models.BonLivraison;
import com.iss4u.erp.services.modules.stock.domain.dto.bonlivraison.request.BonLivraisonRequest;
import com.iss4u.erp.services.modules.stock.domain.dto.bonlivraison.response.BonLivraisonResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BonLivraisonMapper {
    
    @Mapping(target = "articleId", source = "articlesLivres.id")
    @Mapping(target = "articleCode", source = "articlesLivres.codeArticle")
    @Mapping(target = "articleNom", source = "articlesLivres.nomArticle")
    @Mapping(target = "articlePrixVente", source = "articlesLivres.prixVente")
    @Mapping(target = "articleUnite", source = "articlesLivres.unite")
    @Mapping(target = "taxeId", source = "taxes.id")
    @Mapping(target = "taxeNom", source = "taxes.nom")
    BonLivraisonResponse toResponse(BonLivraison entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "articlesLivres", ignore = true)
    @Mapping(target = "taxes", ignore = true)
    BonLivraison toEntity(BonLivraisonRequest dto);

    @Mapping(target = "articlesLivres", ignore = true)
    @Mapping(target = "taxes", ignore = true)
    void updateFromDto(BonLivraisonRequest dto, @MappingTarget BonLivraison entity);
}