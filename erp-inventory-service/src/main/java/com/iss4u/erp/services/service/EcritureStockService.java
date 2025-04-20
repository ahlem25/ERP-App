package com.iss4u.erp.services.service;

import org.springframework.stereotype.Service;
import com.iss4u.erp.services.modules.stock.domain.repository.EcritureStockRepository;
import com.iss4u.erp.services.modules.stock.domain.mapper.EcritureStockMapper;
import com.iss4u.erp.services.modules.stock.domain.models.EcritureStock;
import com.iss4u.erp.services.modules.stock.domain.dto.ecriturestock.request.EcritureStockRequest;
import com.iss4u.erp.services.modules.stock.domain.dto.ecriturestock.response.EcritureStockResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EcritureStockService {
    private final EcritureStockRepository repository;
    private final EcritureStockMapper mapper;
    
    public List<EcritureStockResponse> findAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::toResponse)
                         .collect(Collectors.toList());
    }

    public Optional<EcritureStockResponse> findById(Long id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    public EcritureStockResponse save(EcritureStockRequest request) {
        EcritureStock entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    public EcritureStockResponse update(Long id, EcritureStockRequest request) {
        EcritureStock existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("EcritureStock not found with id " + id));
        
        mapper.updateFromDto(request, existingEntity);
        
        EcritureStock savedEntity = repository.save(existingEntity);
        return mapper.toResponse(savedEntity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}