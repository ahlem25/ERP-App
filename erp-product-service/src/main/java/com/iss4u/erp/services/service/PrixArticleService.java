package com.iss4u.erp.services.service;

import org.springframework.stereotype.Service;
import com.iss4u.erp.services.modules.achat.domain.common.repository.PrixArticleRepository;
import com.iss4u.erp.services.modules.achat.domain.common.mapper.PrixArticleMapper;
import com.iss4u.erp.services.modules.achat.domain.common.models.PrixArticle;
import com.iss4u.erp.services.modules.achat.domain.common.dto.prixarticle.request.PrixArticleRequest;
import com.iss4u.erp.services.modules.achat.domain.common.dto.prixarticle.response.PrixArticleResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrixArticleService {

    private final PrixArticleRepository repository;
    private final PrixArticleMapper mapper;
    
    public List<PrixArticleResponse> findAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::toResponse)
                         .collect(Collectors.toList());
    }

    public Optional<PrixArticleResponse> findById(Long id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    public PrixArticleResponse save(PrixArticleRequest request) {
        PrixArticle entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    public PrixArticleResponse update(Long id, PrixArticleRequest request) {
        PrixArticle existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("PrixArticle not found with id " + id));
        
        mapper.updateFromDto(request, existingEntity);
        
        PrixArticle savedEntity = repository.save(existingEntity);
        return mapper.toResponse(savedEntity);
    }



    public void delete(Long id) {
        repository.deleteById(id);
    }
}