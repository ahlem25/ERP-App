package com.iss4u.erp.services.service;

import org.springframework.stereotype.Service;
import com.iss4u.erp.services.modules.achat.domain.common.repository.ListePrixRepository;
import com.iss4u.erp.services.modules.achat.domain.common.mapper.ListePrixMapper;
import com.iss4u.erp.services.modules.achat.domain.common.models.ListePrix;
import com.iss4u.erp.services.modules.achat.domain.common.dto.listeprix.request.ListePrixRequest;
import com.iss4u.erp.services.modules.achat.domain.common.dto.listeprix.response.ListePrixResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ListePrixService {

    private final ListePrixRepository repository;
    private final ListePrixMapper mapper;

    public List<ListePrixResponse> findAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::toResponse)
                         .collect(Collectors.toList());
    }

    public Optional<ListePrixResponse> findById(Long id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    public ListePrixResponse save(ListePrixRequest request) {
        ListePrix entity = mapper.toEntity(request);
        
        // Gérer la collection de prix articles
        updatePrixArticles(entity, request.getPrixArticles());
        
        return mapper.toResponse(repository.save(entity));
    }

    public ListePrixResponse update(Long id, ListePrixRequest request) {
        ListePrix existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ListePrix not found with id " + id));
        
        // Mapper les données de base (sans la collection)
        mapper.updateFromDto(request, existingEntity);
        
        // Gérer la collection de prix articles
        updatePrixArticles(existingEntity, request.getPrixArticles());
        
        ListePrix savedEntity = repository.save(existingEntity);
        return mapper.toResponse(savedEntity);
    }



    public void delete(Long id) {
        repository.deleteById(id);
    }
    
    /**
     * Méthode utilitaire pour gérer la collection de prix articles
     * et éviter l'erreur orphanRemoval
     */
    private void updatePrixArticles(ListePrix entity, List<com.iss4u.erp.services.modules.achat.domain.common.dto.listeprix.request.PrixArticleRequest> prixArticleRequests) {
        // Initialiser la collection si elle est null
        if (entity.getPrixArticles() == null) {
            entity.setPrixArticles(new java.util.ArrayList<>());
        }
        
        if (prixArticleRequests != null) {
            // Vider la collection existante
            entity.getPrixArticles().clear();
            
            // Ajouter les nouveaux prix articles
            for (com.iss4u.erp.services.modules.achat.domain.common.dto.listeprix.request.PrixArticleRequest prixArticleRequest : prixArticleRequests) {
                com.iss4u.erp.services.modules.achat.domain.common.models.PrixArticle prixArticle = new com.iss4u.erp.services.modules.achat.domain.common.models.PrixArticle();
                prixArticle.setCodeArticle(prixArticleRequest.getCodeArticle());
                prixArticle.setUniteDeMesure(prixArticleRequest.getUniteDeMesure());
                prixArticle.setUniteEmballage(prixArticleRequest.getUniteEmballage());
                prixArticle.setQuantiteParUOM(prixArticleRequest.getQuantiteParUOM());
                prixArticle.setNumeroLot(prixArticleRequest.getNumeroLot());
                prixArticle.setAchat(prixArticleRequest.isAchat());
                prixArticle.setVente(prixArticleRequest.isVente());
                prixArticle.setDevise(prixArticleRequest.getDevise());
                prixArticle.setTaux(prixArticleRequest.getTaux());
                prixArticle.setValableAPartirDu(prixArticleRequest.getValableAPartirDu());
                prixArticle.setValableJusquau(prixArticleRequest.getValableJusquau());
                prixArticle.setDelaiLivraisonJours(prixArticleRequest.getDelaiLivraisonJours());
                prixArticle.setNote(prixArticleRequest.getNote());
                prixArticle.setListeDePrix(entity); // IMPORTANT: définir la relation inverse
                entity.getPrixArticles().add(prixArticle);
            }
        }
    }
}