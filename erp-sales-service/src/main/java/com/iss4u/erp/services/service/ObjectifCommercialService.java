package com.iss4u.erp.services.service;

import org.springframework.stereotype.Service;
import com.iss4u.erp.services.modules.vente.domain.sales.repository.ObjectifCommercialRepository;
import com.iss4u.erp.services.modules.vente.domain.sales.mapper.ObjectifCommercialMapper;
import com.iss4u.erp.services.modules.vente.domain.sales.models.ObjectifCommercial;
import com.iss4u.erp.services.modules.vente.domain.sales.dto.objectifcommercial.request.ObjectifCommercialRequest;
import com.iss4u.erp.services.modules.vente.domain.sales.dto.objectifcommercial.response.ObjectifCommercialResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ObjectifCommercialService {

    private final ObjectifCommercialRepository repository;
    private final ObjectifCommercialMapper mapper;
    
    public List<ObjectifCommercialResponse> findAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::toResponse)
                         .collect(Collectors.toList());
    }

    public Optional<ObjectifCommercialResponse> findById(Long id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    public ObjectifCommercialResponse save(ObjectifCommercialRequest request) {
        ObjectifCommercial entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    public ObjectifCommercialResponse update(Long id, ObjectifCommercialRequest request) {
        ObjectifCommercial existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ObjectifCommercial not found with id " + id));
        
        mapper.updateFromDto(request, existingEntity);
        
        ObjectifCommercial savedEntity = repository.save(existingEntity);
        return mapper.toResponse(savedEntity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}