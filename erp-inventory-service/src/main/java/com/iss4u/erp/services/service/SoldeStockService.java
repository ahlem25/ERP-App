package com.iss4u.erp.services.service;

import org.springframework.stereotype.Service;
import com.iss4u.erp.services.modules.stock.domain.repository.SoldeStockRepository;
import com.iss4u.erp.services.modules.stock.domain.mapper.SoldeStockMapper;
import com.iss4u.erp.services.modules.stock.domain.models.SoldeStock;
import com.iss4u.erp.services.modules.stock.domain.dto.soldestock.request.SoldeStockRequest;
import com.iss4u.erp.services.modules.stock.domain.dto.soldestock.response.SoldeStockResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SoldeStockService {

    private final SoldeStockRepository repository;
    private final SoldeStockMapper mapper;
    
    public List<SoldeStockResponse> findAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::toResponse)
                         .collect(Collectors.toList());
    }

    public Optional<SoldeStockResponse> findById(Long id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    public SoldeStockResponse save(SoldeStockRequest request) {
        SoldeStock entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    public SoldeStockResponse update(Long id, SoldeStockRequest request) {
        SoldeStock existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("SoldeStock not found with id " + id));
        
        mapper.updateFromDto(request, existingEntity);
        
        SoldeStock savedEntity = repository.save(existingEntity);
        return mapper.toResponse(savedEntity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}