package com.iss4u.erp.services.service;

import org.springframework.stereotype.Service;
import com.iss4u.erp.services.modules.core.domain.repository.CommentaireRepository;
import com.iss4u.erp.services.modules.core.domain.mapper.CommentaireMapper;
import com.iss4u.erp.services.modules.core.domain.models.Commentaire;
import com.iss4u.erp.services.modules.core.domain.dto.commentaire.request.CommentaireRequest;
import com.iss4u.erp.services.modules.core.domain.dto.commentaire.response.CommentaireResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentaireService {

    private final CommentaireRepository repository;
    private final CommentaireMapper mapper;
    
    public List<CommentaireResponse> findAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::toResponse)
                         .collect(Collectors.toList());
    }

    public Optional<CommentaireResponse> findById(Long id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    public CommentaireResponse save(CommentaireRequest request) {
        Commentaire entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    public CommentaireResponse update(Long id, CommentaireRequest request) {
        Commentaire existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commentaire not found with id " + id));
        
        mapper.updateFromDto(request, existingEntity);
        
        Commentaire savedEntity = repository.save(existingEntity);
        return mapper.toResponse(savedEntity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}