package com.iss4u.erp.services.service;

import org.springframework.stereotype.Service;
import com.iss4u.erp.services.modules.vente.domain.billing.repository.DevisRepository;
import com.iss4u.erp.services.modules.vente.domain.billing.mapper.DevisMapper;
import com.iss4u.erp.services.modules.vente.domain.billing.models.Devis;
import com.iss4u.erp.services.modules.vente.domain.billing.dto.devis.request.DevisRequest;
import com.iss4u.erp.services.modules.vente.domain.billing.dto.devis.response.DevisResponse;
import com.iss4u.erp.services.modules.achat.domain.common.repository.ArticleRepository;
import com.iss4u.erp.services.modules.achat.domain.common.models.Article;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DevisService {

    private final DevisRepository repository;
    private final DevisMapper mapper;
    private final ArticleRepository articleRepository;
    
    @PersistenceContext
    private EntityManager entityManager;

    public List<DevisResponse> findAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::toResponse)
                         .collect(Collectors.toList());
    }

    public Optional<DevisResponse> findById(Long id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    public DevisResponse save(DevisRequest request) {
        Devis entity = mapper.toEntity(request);
        
        // Ensure articles are managed
        if (entity.getArticles() != null) {
            List<Article> managedArticles = entity.getArticles().stream()
                .map(article -> {
                    if (article.getId() != null) {
                        return articleRepository.findById(article.getId())
                            .orElseThrow(() -> new RuntimeException("Article not found with id " + article.getId()));
                    }
                    return article;
                })
                .collect(Collectors.toList());
            entity.setArticles(managedArticles);
        }
        
        return mapper.toResponse(repository.save(entity));
    }

    public DevisResponse update(Long id, DevisRequest request) {
        Devis existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Devis not found with id " + id));
        
        mapper.updateFromDto(request, existingEntity);
        
        // Ensure articles are managed
        if (existingEntity.getArticles() != null) {
            List<Article> managedArticles = existingEntity.getArticles().stream()
                .map(article -> {
                    if (article.getId() != null) {
                        return articleRepository.findById(article.getId())
                            .orElseThrow(() -> new RuntimeException("Article not found with id " + article.getId()));
                    }
                    return article;
                })
                .collect(Collectors.toList());
            existingEntity.setArticles(managedArticles);
        }
        
        Devis savedEntity = repository.save(existingEntity);
        return mapper.toResponse(savedEntity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}