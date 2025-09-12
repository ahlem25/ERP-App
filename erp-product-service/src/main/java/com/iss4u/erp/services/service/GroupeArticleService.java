package com.iss4u.erp.services.service;

import org.springframework.stereotype.Service;
import com.iss4u.erp.services.modules.achat.domain.common.repository.GroupeArticleRepository;
import com.iss4u.erp.services.modules.achat.domain.common.mapper.GroupeArticleMapper;
import com.iss4u.erp.services.modules.achat.domain.common.models.GroupeArticle;
import com.iss4u.erp.services.modules.achat.domain.common.dto.groupearticle.request.GroupeArticleRequest;
import com.iss4u.erp.services.modules.achat.domain.common.dto.groupearticle.response.GroupeArticleResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GroupeArticleService {

    private final GroupeArticleRepository repository;
    private final GroupeArticleMapper mapper;

    public List<GroupeArticleResponse> findAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::toResponse)
                         .collect(Collectors.toList());
    }

    public Optional<GroupeArticleResponse> findById(Long id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    public GroupeArticleResponse save(GroupeArticleRequest request) {
        GroupeArticle entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    public GroupeArticleResponse update(Long id, GroupeArticleRequest request) {
        GroupeArticle existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("GroupeArticle not found with id " + id));
        
        mapper.updateFromDto(request, existingEntity);
        
        GroupeArticle savedEntity = repository.save(existingEntity);
        return mapper.toResponse(savedEntity);
    }



    public void delete(Long id) {
        repository.deleteById(id);
    }
}