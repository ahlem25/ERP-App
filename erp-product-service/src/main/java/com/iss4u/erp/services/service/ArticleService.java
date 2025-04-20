package com.iss4u.erp.services.service;

import com.iss4u.erp.services.modules.achat.domain.common.models.Article;
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

    public ArticleResponse update(Long id, ArticleRequest request) {
        Article existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Article not found with id " + id));
        
        mapper.updateFromDto(request, existingEntity);
        
        Article savedEntity = repository.save(existingEntity);
        return mapper.toResponse(savedEntity);
    }



    public void delete(Long id) {
        repository.deleteById(id);
    }
}