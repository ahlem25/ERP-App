package com.iss4u.erp.services.service;

import org.springframework.stereotype.Service;
import com.iss4u.erp.services.modules.stock.domain.repository.NumeroSerieRepository;
import com.iss4u.erp.services.modules.stock.domain.mapper.NumeroSerieMapper;
import com.iss4u.erp.services.modules.stock.domain.models.NumeroSerie;
import com.iss4u.erp.services.modules.stock.domain.dto.numeroserie.request.NumeroSerieRequest;
import com.iss4u.erp.services.modules.stock.domain.dto.numeroserie.response.NumeroSerieResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NumeroSerieService {

    private final NumeroSerieRepository repository;
    private final NumeroSerieMapper mapper;
    
    public List<NumeroSerieResponse> findAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::toResponse)
                         .collect(Collectors.toList());
    }

    public Optional<NumeroSerieResponse> findById(Long id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    public NumeroSerieResponse save(NumeroSerieRequest request) {
        NumeroSerie entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    public NumeroSerieResponse update(Long id, NumeroSerieRequest request) {
        NumeroSerie existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("NumeroSerie not found with id " + id));
        
        mapper.updateFromDto(request, existingEntity);
        
        NumeroSerie savedEntity = repository.save(existingEntity);
        return mapper.toResponse(savedEntity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}