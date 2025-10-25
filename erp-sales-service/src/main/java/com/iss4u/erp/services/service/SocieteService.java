package com.iss4u.erp.services.service;

import org.springframework.stereotype.Service;
import com.iss4u.erp.services.modules.vente.domain.sales.repository.SocieteRepository;
import com.iss4u.erp.services.modules.vente.domain.sales.mapper.SocieteMapper;
import com.iss4u.erp.services.modules.vente.domain.sales.models.Societe;
import com.iss4u.erp.services.modules.vente.domain.sales.dto.societe.request.SocieteRequest;
import com.iss4u.erp.services.modules.vente.domain.sales.dto.societe.response.SocieteResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SocieteService {

    private final SocieteRepository repository;
    private final SocieteMapper mapper;
    
    public List<SocieteResponse> findAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::toResponse)
                         .collect(Collectors.toList());
    }

    public Optional<SocieteResponse> findById(Long id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    public SocieteResponse save(SocieteRequest request) {
        Societe entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    public SocieteResponse update(Long id, SocieteRequest request) {
        Societe existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Societe not found with id " + id));
        
        mapper.updateFromDto(request, existingEntity);
        
        Societe savedEntity = repository.save(existingEntity);
        return mapper.toResponse(savedEntity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}