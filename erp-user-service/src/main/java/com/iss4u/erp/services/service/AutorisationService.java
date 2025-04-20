package com.iss4u.erp.services.service;

import org.springframework.stereotype.Service;
import com.iss4u.erp.services.modules.core.domain.repository.AutorisationRepository;
import com.iss4u.erp.services.modules.core.domain.mapper.AutorisationMapper;
import com.iss4u.erp.services.modules.core.domain.models.Autorisation;
import com.iss4u.erp.services.modules.core.domain.dto.autorisation.request.AutorisationRequest;
import com.iss4u.erp.services.modules.core.domain.dto.autorisation.response.AutorisationResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AutorisationService {

    private final AutorisationRepository repository;
    private final AutorisationMapper mapper;

    public List<AutorisationResponse> findAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::toResponse)
                         .collect(Collectors.toList());
    }

    public Optional<AutorisationResponse> findById(Long id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    public AutorisationResponse save(AutorisationRequest request) {
        Autorisation entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    public AutorisationResponse update(Long id, AutorisationRequest request) {
        Autorisation existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Autorisation not found with id " + id));
        
        mapper.updateFromDto(request, existingEntity);
        
        Autorisation savedEntity = repository.save(existingEntity);
        return mapper.toResponse(savedEntity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}