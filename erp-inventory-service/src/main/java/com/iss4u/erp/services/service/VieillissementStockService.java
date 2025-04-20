package com.iss4u.erp.services.service;

import org.springframework.stereotype.Service;
import com.iss4u.erp.services.modules.stock.domain.repository.VieillissementStockRepository;
import com.iss4u.erp.services.modules.stock.domain.mapper.VieillissementStockMapper;
import com.iss4u.erp.services.modules.stock.domain.models.VieillissementStock;
import com.iss4u.erp.services.modules.stock.domain.dto.vieillissementstock.request.VieillissementStockRequest;
import com.iss4u.erp.services.modules.stock.domain.dto.vieillissementstock.response.VieillissementStockResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VieillissementStockService {

    private final VieillissementStockRepository repository;
    private final VieillissementStockMapper mapper;

    public List<VieillissementStockResponse> findAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::toResponse)
                         .collect(Collectors.toList());
    }

    public Optional<VieillissementStockResponse> findById(Long id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    public VieillissementStockResponse save(VieillissementStockRequest request) {
        VieillissementStock entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    public VieillissementStockResponse update(Long id, VieillissementStockRequest request) {
        VieillissementStock existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("VieillissementStock not found with id " + id));
        
        mapper.updateFromDto(request, existingEntity);
        
        VieillissementStock savedEntity = repository.save(existingEntity);
        return mapper.toResponse(savedEntity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}