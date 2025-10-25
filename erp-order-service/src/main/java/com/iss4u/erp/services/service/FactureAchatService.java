package com.iss4u.erp.services.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.iss4u.erp.services.modules.achat.domain.commande.repository.FactureAchatRepository;
import com.iss4u.erp.services.modules.achat.domain.commande.mapper.FactureAchatMapper;
import com.iss4u.erp.services.modules.achat.domain.commande.models.FactureAchat;
import com.iss4u.erp.services.modules.achat.domain.commande.dto.factureachat.request.FactureAchatRequest;
import com.iss4u.erp.services.modules.achat.domain.commande.dto.factureachat.response.FactureAchatResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FactureAchatService {

    private final FactureAchatRepository repository;
    private final FactureAchatMapper mapper;
    
    public List<FactureAchatResponse> findAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::toResponse)
                         .collect(Collectors.toList());
    }

    public Optional<FactureAchatResponse> findById(Long id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    @Transactional
    public FactureAchatResponse save(FactureAchatRequest request) {
        FactureAchat entity = mapper.toEntity(request);
        
        // Gérer la collection d'articles facturés
        updateArticlesFactures(entity, request.getArticlesFactures());
        
        return mapper.toResponse(repository.save(entity));
    }

    @Transactional
    public FactureAchatResponse update(Long id, FactureAchatRequest request) {
        FactureAchat existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("FactureAchat not found with id " + id));
        
        // Mapper les données de base (sans la collection)
        mapper.updateFromDto(request, existingEntity);
        
        // Gérer la collection d'articles facturés de manière simple
        updateArticlesFacturesSimple(existingEntity, request.getArticlesFactures());
        
        FactureAchat savedEntity = repository.save(existingEntity);
        return mapper.toResponse(savedEntity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
    
    /**
     * Méthode utilitaire pour gérer la collection d'articles facturés
     * et éviter l'erreur orphanRemoval
     */
    private void updateArticlesFactures(FactureAchat entity, List<com.iss4u.erp.services.modules.achat.domain.commande.dto.factureachat.request.ItemRequest> itemRequests) {
        // Créer une nouvelle liste pour éviter les problèmes d'orphanRemoval
        java.util.List<com.iss4u.erp.services.modules.achat.domain.commande.models.Item> newArticlesFactures = new java.util.ArrayList<>();
        
        if (itemRequests != null) {
            // Ajouter les nouveaux items
            for (com.iss4u.erp.services.modules.achat.domain.commande.dto.factureachat.request.ItemRequest itemRequest : itemRequests) {
                com.iss4u.erp.services.modules.achat.domain.commande.models.Item item = new com.iss4u.erp.services.modules.achat.domain.commande.models.Item();
                item.setQuantite(itemRequest.getQuantite());
                item.setPrixUnitaire(itemRequest.getPrixUnitaire());
                item.setCommentaire(itemRequest.getCommentaire());
                item.setFactureAchat(entity);
                newArticlesFactures.add(item);
            }
        }
        
        // Remplacer complètement la collection
        entity.setArticlesFactures(newArticlesFactures);
    }
    
    /**
     * Méthode simple pour la mise à jour des articles facturés
     * Utilise une approche directe sans orphanRemoval
     */
    private void updateArticlesFacturesSimple(FactureAchat entity, List<com.iss4u.erp.services.modules.achat.domain.commande.dto.factureachat.request.ItemRequest> itemRequests) {
        // Initialiser la collection si elle est null
        if (entity.getArticlesFactures() == null) {
            entity.setArticlesFactures(new java.util.ArrayList<>());
        }
        
        // Créer une nouvelle liste complètement
        java.util.List<com.iss4u.erp.services.modules.achat.domain.commande.models.Item> newItems = new java.util.ArrayList<>();
        
        if (itemRequests != null) {
            for (com.iss4u.erp.services.modules.achat.domain.commande.dto.factureachat.request.ItemRequest itemRequest : itemRequests) {
                com.iss4u.erp.services.modules.achat.domain.commande.models.Item item = new com.iss4u.erp.services.modules.achat.domain.commande.models.Item();
                item.setQuantite(itemRequest.getQuantite());
                item.setPrixUnitaire(itemRequest.getPrixUnitaire());
                item.setCommentaire(itemRequest.getCommentaire());
                item.setFactureAchat(entity);
                newItems.add(item);
            }
        }
        
        // Remplacer complètement la collection
        entity.setArticlesFactures(newItems);
    }
    
    /**
     * Méthode sécurisée pour la mise à jour des articles facturés
     * Utilise une approche différente pour éviter les problèmes d'orphanRemoval
     */
    private void updateArticlesFacturesSafely(FactureAchat entity, List<com.iss4u.erp.services.modules.achat.domain.commande.dto.factureachat.request.ItemRequest> itemRequests) {
        // Supprimer tous les articles existants de manière sécurisée
        if (entity.getArticlesFactures() != null) {
            // Créer une copie de la liste pour éviter ConcurrentModificationException
            java.util.List<com.iss4u.erp.services.modules.achat.domain.commande.models.Item> existingItems = 
                new java.util.ArrayList<>(entity.getArticlesFactures());
            
            // Supprimer chaque item individuellement
            for (com.iss4u.erp.services.modules.achat.domain.commande.models.Item item : existingItems) {
                entity.getArticlesFactures().remove(item);
            }
        }
        
        // Ajouter les nouveaux articles
        if (itemRequests != null && !itemRequests.isEmpty()) {
            for (com.iss4u.erp.services.modules.achat.domain.commande.dto.factureachat.request.ItemRequest itemRequest : itemRequests) {
                com.iss4u.erp.services.modules.achat.domain.commande.models.Item item = new com.iss4u.erp.services.modules.achat.domain.commande.models.Item();
                item.setQuantite(itemRequest.getQuantite());
                item.setPrixUnitaire(itemRequest.getPrixUnitaire());
                item.setCommentaire(itemRequest.getCommentaire());
                item.setFactureAchat(entity);
                entity.getArticlesFactures().add(item);
            }
        }
    }
}