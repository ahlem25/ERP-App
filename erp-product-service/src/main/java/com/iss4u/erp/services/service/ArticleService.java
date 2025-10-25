package com.iss4u.erp.services.service;

import com.iss4u.erp.services.modules.achat.domain.common.models.Article;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Service;
import com.iss4u.erp.services.modules.achat.domain.common.repository.ArticleRepository;
import com.iss4u.erp.services.modules.achat.domain.common.mapper.ArticleMapper;
import com.iss4u.erp.services.modules.achat.domain.common.dto.article.request.ArticleRequest;
import com.iss4u.erp.services.modules.achat.domain.common.dto.article.response.ArticleResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository repository;
    private final ArticleMapper mapper;
    private final FileStorageService storageService;

    public List<ArticleResponse> findAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::toResponse)
                         .collect(Collectors.toList());
    }

    public Optional<ArticleResponse> findById(Long id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    public ArticleResponse save(ArticleRequest request) {
        Article entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    public ArticleResponse saveWithImage(ArticleRequest request, MultipartFile file) throws java.io.IOException {
        if (file != null && !file.isEmpty()) {
            String filename = storageService.store(file);
            request.setImage(filename);
        }
        return save(request);
    }

    public ArticleResponse update(Long id, ArticleRequest request) {
        Article existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Article not found with id " + id));
        
        // Éviter d'écraser l'image existante par une chaîne vide
        if (request.getImage() != null && request.getImage().isBlank()) {
            request.setImage(null);
        }

        mapper.updateFromDto(request, existingEntity);
        
        Article savedEntity = repository.save(existingEntity);
        return mapper.toResponse(savedEntity);
    }

    public ArticleResponse updateWithImage(Long id, ArticleRequest request, MultipartFile file) throws java.io.IOException {
        if (file != null && !file.isEmpty()) {
            String filename = storageService.store(file);
            request.setImage(filename);
        }
        return update(id, request);
    }



    public void delete(Long id) {
        repository.deleteById(id);
    }
}