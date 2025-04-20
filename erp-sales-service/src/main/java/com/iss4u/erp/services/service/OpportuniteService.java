package com.iss4u.erp.services.service;

import org.springframework.stereotype.Service;
import com.iss4u.erp.services.modules.vente.domain.sales.repository.OpportuniteRepository;
import com.iss4u.erp.services.modules.vente.domain.sales.mapper.OpportuniteMapper;
import com.iss4u.erp.services.modules.vente.domain.sales.models.Opportunite;
import com.iss4u.erp.services.modules.vente.domain.sales.dto.opportunite.request.OpportuniteRequest;
import com.iss4u.erp.services.modules.vente.domain.sales.dto.opportunite.response.OpportuniteResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OpportuniteService {

    private final OpportuniteRepository repository;
    private final OpportuniteMapper mapper;
    
    public List<OpportuniteResponse> findAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::toResponse)
                         .collect(Collectors.toList());
    }

    public Optional<OpportuniteResponse> findById(Long id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    public OpportuniteResponse save(OpportuniteRequest request) {
        Opportunite entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    public OpportuniteResponse update(Long id, OpportuniteRequest request) {
        Opportunite existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Opportunite not found with id " + id));
        
        mapper.updateFromDto(request, existingEntity);
        
        Opportunite savedEntity = repository.save(existingEntity);
        return mapper.toResponse(savedEntity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}