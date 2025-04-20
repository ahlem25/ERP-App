package com.iss4u.erp.services.service;

import org.springframework.stereotype.Service;
import com.iss4u.erp.services.modules.stock.domain.repository.LotRepository;
import com.iss4u.erp.services.modules.stock.domain.mapper.LotMapper;
import com.iss4u.erp.services.modules.stock.domain.models.Lot;
import com.iss4u.erp.services.modules.stock.domain.dto.lot.request.LotRequest;
import com.iss4u.erp.services.modules.stock.domain.dto.lot.response.LotResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LotService {

    private final LotRepository repository;
    private final LotMapper mapper;
    
    public List<LotResponse> findAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::toResponse)
                         .collect(Collectors.toList());
    }

    public Optional<LotResponse> findById(Long id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    public LotResponse save(LotRequest request) {
        Lot entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    public LotResponse update(Long id, LotRequest request) {
        Lot existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lot not found with id " + id));
        
        mapper.updateFromDto(request, existingEntity);
        
        Lot savedEntity = repository.save(existingEntity);
        return mapper.toResponse(savedEntity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}