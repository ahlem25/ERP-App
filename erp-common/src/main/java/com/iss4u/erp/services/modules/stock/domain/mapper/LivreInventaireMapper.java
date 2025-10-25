package com.iss4u.erp.services.modules.stock.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import com.iss4u.erp.services.modules.stock.domain.dto.livreinventaire.request.LivreInventaireRequest;
import com.iss4u.erp.services.modules.stock.domain.dto.livreinventaire.response.LivreInventaireResponse;
import com.iss4u.erp.services.modules.stock.domain.models.LivreInventaire;

@Mapper(componentModel = "spring")
public interface LivreInventaireMapper {
    
    @Mapping(target = "articleId", source = "article.id")
    @Mapping(target = "articleNom", source = "article.nomArticle")
    @Mapping(target = "article", source = "article")
    @Mapping(target = "entrepotId", source = "entrepot.id")
    @Mapping(target = "entrepotNom", source = "entrepot.nom")
    @Mapping(target = "entrepot", source = "entrepot")
    @Mapping(target = "lotId", source = "lot.id")
    @Mapping(target = "mouvementId", source = "mouvement.id")
    @Mapping(target = "numeroSerieId", source = "numeroSerie.id")
    LivreInventaireResponse toResponse(LivreInventaire entity);
    
    @Mapping(target = "article", ignore = true)
    @Mapping(target = "entrepot", ignore = true)
    @Mapping(target = "lot", ignore = true)
    @Mapping(target = "mouvement", ignore = true)
    @Mapping(target = "numeroSerie", ignore = true)
    LivreInventaire toEntity(LivreInventaireRequest dto);
    
    @Mapping(target = "article", ignore = true)
    @Mapping(target = "entrepot", ignore = true)
    @Mapping(target = "lot", ignore = true)
    @Mapping(target = "mouvement", ignore = true)
    @Mapping(target = "numeroSerie", ignore = true)
    void updateFromDto(LivreInventaireRequest dto, @MappingTarget LivreInventaire entity);
}