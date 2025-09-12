package com.iss4u.erp.services.service;

import org.springframework.stereotype.Service;
import com.iss4u.erp.services.modules.stock.domain.repository.EntrepotRepository;
import com.iss4u.erp.services.modules.stock.domain.mapper.EntrepotMapper;
import com.iss4u.erp.services.modules.stock.domain.models.Entrepot;
import com.iss4u.erp.services.modules.stock.domain.dto.entrepot.request.EntrepotRequest;
import com.iss4u.erp.services.modules.stock.domain.dto.entrepot.response.EntrepotResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EntrepotService {

    private final EntrepotRepository repository;
    private final EntrepotMapper mapper;
    
    public List<EntrepotResponse> findAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::toResponse)
                         .collect(Collectors.toList());
    }

    public Optional<EntrepotResponse> findById(Long id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    public EntrepotResponse save(EntrepotRequest request) {
        Entrepot entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    public EntrepotResponse update(Long id, EntrepotRequest request) {
        Entrepot existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entrepot not found with id " + id));
        
        mapper.updateFromDto(request, existingEntity);
        
        Entrepot savedEntity = repository.save(existingEntity);
        return mapper.toResponse(savedEntity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}