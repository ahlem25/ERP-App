package com.iss4u.erp.services.service;

import org.springframework.stereotype.Service;
import com.iss4u.erp.services.modules.vente.domain.billing.repository.FactureVenteRepository;
import com.iss4u.erp.services.modules.vente.domain.billing.mapper.FactureVenteMapper;
import com.iss4u.erp.services.modules.vente.domain.billing.models.FactureVente;
import com.iss4u.erp.services.modules.vente.domain.billing.dto.facturevente.request.FactureVenteRequest;
import com.iss4u.erp.services.modules.vente.domain.billing.dto.facturevente.response.FactureVenteResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class FactureVenteService {

    private final FactureVenteRepository repository;
    private final FactureVenteMapper mapper;
    
    public List<FactureVenteResponse> findAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::toResponse)
                         .collect(Collectors.toList());
    }

    public Optional<FactureVenteResponse> findById(Long id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    public FactureVenteResponse save(FactureVenteRequest request) {
        FactureVente entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    public FactureVenteResponse update(Long id, FactureVenteRequest request) {
        FactureVente existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("FactureVente not found with id " + id));
        
        mapper.updateFromDto(request, existingEntity);
        
        FactureVente savedEntity = repository.save(existingEntity);
        return mapper.toResponse(savedEntity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}