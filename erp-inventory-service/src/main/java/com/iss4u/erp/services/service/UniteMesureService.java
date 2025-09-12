package com.iss4u.erp.services.service;

import org.springframework.stereotype.Service;
import com.iss4u.erp.services.modules.stock.domain.repository.UniteMesureRepository;
import com.iss4u.erp.services.modules.stock.domain.mapper.UniteMesureMapper;
import com.iss4u.erp.services.modules.stock.domain.models.UniteMesure;
import com.iss4u.erp.services.modules.stock.domain.dto.unitemesure.request.UniteMesureRequest;
import com.iss4u.erp.services.modules.stock.domain.dto.unitemesure.response.UniteMesureResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UniteMesureService {

    private final UniteMesureRepository repository;
    private final UniteMesureMapper mapper;

    public List<UniteMesureResponse> findAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::toResponse)
                         .collect(Collectors.toList());
    }

    public Optional<UniteMesureResponse> findById(Long id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    public UniteMesureResponse save(UniteMesureRequest request) {
        UniteMesure entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    public UniteMesureResponse update(Long id, UniteMesureRequest request) {
        UniteMesure existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("UniteMesure not found with id " + id));
        
        mapper.updateFromDto(request, existingEntity);
        
        UniteMesure savedEntity = repository.save(existingEntity);
        return mapper.toResponse(savedEntity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}