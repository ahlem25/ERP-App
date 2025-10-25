package com.iss4u.erp.services.modules.achat.domain.common.mapper;

import com.iss4u.erp.services.modules.achat.domain.common.models.Article;
import org.mapstruct.Mapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import com.iss4u.erp.services.modules.achat.domain.common.dto.article.request.ArticleRequest;
import com.iss4u.erp.services.modules.achat.domain.common.dto.article.response.ArticleResponse;

@Mapper(componentModel = "spring")
public interface ArticleMapper {
    
    @Mapping(target = "groupeArticleId", source = "groupeArticle.id")
    @Mapping(target = "groupeArticleNom", source = "groupeArticle.nom")
    @Mapping(target = "prixArticleIds", source = "prixArticles", qualifiedByName = "mapPrixArticleIds")
    @Mapping(target = "demandeMaterielIds", source = "demandesMateriel", qualifiedByName = "mapDemandeMaterielIds")
    @Mapping(target = "commandeClientIds", source = "commandes", qualifiedByName = "mapCommandeClientIds")
    @Mapping(target = "bonLivraisonIds", ignore = true)
    @Mapping(target = "bonLivraisons", ignore = true)
    @Mapping(target = "soldeStockIds", source = "soldeStocks", qualifiedByName = "mapSoldeStockIds")
    @Mapping(target = "ecritureStockIds", source = "ecritureStocks", qualifiedByName = "mapEcritureStockIds")
    @Mapping(target = "livreInventaireIds", source = "livreInventaires", qualifiedByName = "mapLivreInventaireIds")
    @Mapping(target = "vieillissementStockIds", source = "vieillissementStocks", qualifiedByName = "mapVieillissementStockIds")
    ArticleResponse toResponse(Article entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "groupeArticle", ignore = true)
    @Mapping(target = "prixArticles", ignore = true)
    @Mapping(target = "demandesMateriel", ignore = true)
    @Mapping(target = "commandes", ignore = true)
    @Mapping(target = "bonLivraisons", ignore = true)
    @Mapping(target = "soldeStocks", ignore = true)
    @Mapping(target = "ecritureStocks", ignore = true)
    @Mapping(target = "livreInventaires", ignore = true)
    @Mapping(target = "vieillissementStocks", ignore = true)
    Article toEntity(ArticleRequest dto);

    @Mapping(target = "groupeArticle", ignore = true)
    @Mapping(target = "prixArticles", ignore = true)
    @Mapping(target = "demandesMateriel", ignore = true)
    @Mapping(target = "commandes", ignore = true)
    @Mapping(target = "bonLivraisons", ignore = true)
    @Mapping(target = "soldeStocks", ignore = true)
    @Mapping(target = "ecritureStocks", ignore = true)
    @Mapping(target = "livreInventaires", ignore = true)
    @Mapping(target = "vieillissementStocks", ignore = true)
    void updateFromDto(ArticleRequest dto, @MappingTarget Article entity);
    
    // Méthodes de mapping pour les IDs
    @org.mapstruct.Named("mapPrixArticleIds")
    default java.util.List<Long> mapPrixArticleIds(java.util.List<com.iss4u.erp.services.modules.achat.domain.common.models.PrixArticle> prixArticles) {
        if (prixArticles == null) return null;
        return prixArticles.stream().map(com.iss4u.erp.services.modules.achat.domain.common.models.PrixArticle::getId).collect(java.util.stream.Collectors.toList());
    }
    
    @org.mapstruct.Named("mapDemandeMaterielIds")
    default java.util.List<Long> mapDemandeMaterielIds(java.util.List<com.iss4u.erp.services.modules.achat.domain.commande.models.DemandeMateriel> demandesMateriel) {
        if (demandesMateriel == null) return null;
        return demandesMateriel.stream().map(com.iss4u.erp.services.modules.achat.domain.commande.models.DemandeMateriel::getId).collect(java.util.stream.Collectors.toList());
    }
    
    @org.mapstruct.Named("mapCommandeClientIds")
    default java.util.List<Long> mapCommandeClientIds(java.util.List<com.iss4u.erp.services.modules.vente.domain.billing.models.CommandeClient> commandes) {
        if (commandes == null) return null;
        return commandes.stream().map(com.iss4u.erp.services.modules.vente.domain.billing.models.CommandeClient::getId).collect(java.util.stream.Collectors.toList());
    }
    
    @org.mapstruct.Named("mapBonLivraisonIds")
    default java.util.List<Long> mapBonLivraisonIds(java.util.List<com.iss4u.erp.services.modules.stock.domain.models.BonLivraison> bonLivraisons) {
        if (bonLivraisons == null) return null;
        return bonLivraisons.stream().map(com.iss4u.erp.services.modules.stock.domain.models.BonLivraison::getId).collect(java.util.stream.Collectors.toList());
    }
    
    @org.mapstruct.Named("mapSoldeStockIds")
    default java.util.List<Long> mapSoldeStockIds(java.util.List<com.iss4u.erp.services.modules.stock.domain.models.SoldeStock> soldeStocks) {
        if (soldeStocks == null) return null;
        return soldeStocks.stream().map(com.iss4u.erp.services.modules.stock.domain.models.SoldeStock::getId).collect(java.util.stream.Collectors.toList());
    }
    
    @org.mapstruct.Named("mapEcritureStockIds")
    default java.util.List<Long> mapEcritureStockIds(java.util.List<com.iss4u.erp.services.modules.stock.domain.models.EcritureStock> ecritureStocks) {
        if (ecritureStocks == null) return null;
        return ecritureStocks.stream().map(com.iss4u.erp.services.modules.stock.domain.models.EcritureStock::getId).collect(java.util.stream.Collectors.toList());
    }
    
    @org.mapstruct.Named("mapLivreInventaireIds")
    default java.util.List<Long> mapLivreInventaireIds(java.util.List<com.iss4u.erp.services.modules.stock.domain.models.LivreInventaire> livreInventaires) {
        if (livreInventaires == null) return null;
        return livreInventaires.stream().map(com.iss4u.erp.services.modules.stock.domain.models.LivreInventaire::getId).collect(java.util.stream.Collectors.toList());
    }
    
    @org.mapstruct.Named("mapVieillissementStockIds")
    default java.util.List<Long> mapVieillissementStockIds(java.util.List<com.iss4u.erp.services.modules.stock.domain.models.VieillissementStock> vieillissementStocks) {
        if (vieillissementStocks == null) return null;
        return vieillissementStocks.stream().map(com.iss4u.erp.services.modules.stock.domain.models.VieillissementStock::getId).collect(java.util.stream.Collectors.toList());
    }
}