package com.iss4u.erp.services.service;

import org.springframework.stereotype.Service;
import com.iss4u.erp.services.modules.stock.domain.repository.MarqueRepository;
import com.iss4u.erp.services.modules.stock.domain.mapper.MarqueMapper;
import com.iss4u.erp.services.modules.stock.domain.models.Marque;
import com.iss4u.erp.services.modules.stock.domain.dto.marque.request.MarqueRequest;
import com.iss4u.erp.services.modules.stock.domain.dto.marque.response.MarqueResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MarqueService {

    private final MarqueRepository repository;
    private final MarqueMapper mapper;

    public List<MarqueResponse> findAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::toResponse)
                         .collect(Collectors.toList());
    }

    public Optional<MarqueResponse> findById(Long id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    public MarqueResponse save(MarqueRequest request) {
        Marque entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    public MarqueResponse update(Long id, MarqueRequest request) {
        Marque existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Marque not found with id " + id));
        
        mapper.updateFromDto(request, existingEntity);
        
        Marque savedEntity = repository.save(existingEntity);
        return mapper.toResponse(savedEntity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}