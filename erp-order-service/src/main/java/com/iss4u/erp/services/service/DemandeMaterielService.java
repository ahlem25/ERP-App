package com.iss4u.erp.services.service;

import org.springframework.stereotype.Service;
import com.iss4u.erp.services.modules.achat.domain.commande.repository.DemandeMaterielRepository;
import com.iss4u.erp.services.modules.achat.domain.commande.mapper.DemandeMaterielMapper;
import com.iss4u.erp.services.modules.achat.domain.commande.models.DemandeMateriel;
import com.iss4u.erp.services.modules.achat.domain.commande.dto.demandemateriel.request.DemandeMaterielRequest;
import com.iss4u.erp.services.modules.achat.domain.commande.dto.demandemateriel.response.DemandeMaterielResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DemandeMaterielService {

    private final DemandeMaterielRepository repository;
    private final DemandeMaterielMapper mapper;
    
    public List<DemandeMaterielResponse> findAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::toResponse)
                         .collect(Collectors.toList());
    }

    public Optional<DemandeMaterielResponse> findById(Long id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    public DemandeMaterielResponse save(DemandeMaterielRequest request) {
        DemandeMateriel entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    public DemandeMaterielResponse update(Long id, DemandeMaterielRequest request) {
        DemandeMateriel existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("DemandeMateriel not found with id " + id));
        
        mapper.updateFromDto(request, existingEntity);
        
        DemandeMateriel savedEntity = repository.save(existingEntity);
        return mapper.toResponse(savedEntity);
    }



    public void delete(Long id) {
        repository.deleteById(id);
    }
}