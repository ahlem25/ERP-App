package com.iss4u.erp.services.service;

import org.springframework.stereotype.Service;
import com.iss4u.erp.services.modules.core.domain.repository.ActiviteRepository;
import com.iss4u.erp.services.modules.core.domain.mapper.ActiviteMapper;
import com.iss4u.erp.services.modules.core.domain.models.Activite;
import com.iss4u.erp.services.modules.core.domain.dto.activite.request.ActiviteRequest;
import com.iss4u.erp.services.modules.core.domain.dto.activite.response.ActiviteResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ActiviteService {

    private final ActiviteRepository repository;
    private final ActiviteMapper mapper;
    
    public List<ActiviteResponse> findAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::toResponse)
                         .collect(Collectors.toList());
    }

    public Optional<ActiviteResponse> findById(Long id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    public ActiviteResponse save(ActiviteRequest request) {
        Activite entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    public ActiviteResponse update(Long id, ActiviteRequest request) {
        Activite existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Activite not found with id " + id));
        
        mapper.updateFromDto(request, existingEntity);
        
        Activite savedEntity = repository.save(existingEntity);
        return mapper.toResponse(savedEntity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}