package com.iss4u.erp.services.service;

import org.springframework.stereotype.Service;
import com.iss4u.erp.services.modules.vente.domain.sales.repository.TaxeRepository;
import com.iss4u.erp.services.modules.vente.domain.sales.mapper.TaxeMapper;
import com.iss4u.erp.services.modules.vente.domain.sales.models.Taxe;
import com.iss4u.erp.services.modules.vente.domain.sales.dto.taxe.request.TaxeRequest;
import com.iss4u.erp.services.modules.vente.domain.sales.dto.taxe.response.TaxeResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaxeService {

    private final TaxeRepository repository;
    private final TaxeMapper mapper;
    
    public List<TaxeResponse> findAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::toResponse)
                         .collect(Collectors.toList());
    }

    public Optional<TaxeResponse> findById(Long id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    public TaxeResponse save(TaxeRequest request) {
        Taxe entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    public TaxeResponse update(Long id, TaxeRequest request) {
        Taxe existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Taxe not found with id " + id));
        
        mapper.updateFromDto(request, existingEntity);
        
        Taxe savedEntity = repository.save(existingEntity);
        return mapper.toResponse(savedEntity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}