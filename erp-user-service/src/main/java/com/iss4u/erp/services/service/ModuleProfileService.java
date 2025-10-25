package com.iss4u.erp.services.service;

import org.springframework.stereotype.Service;
import com.iss4u.erp.services.modules.core.domain.repository.ModuleProfileRepository;
import com.iss4u.erp.services.modules.core.domain.mapper.ModuleProfileMapper;
import com.iss4u.erp.services.modules.core.domain.models.ModuleProfile;
import com.iss4u.erp.services.modules.core.domain.dto.moduleprofile.request.ModuleProfileRequest;
import com.iss4u.erp.services.modules.core.domain.dto.moduleprofile.response.ModuleProfileResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ModuleProfileService {

    private final ModuleProfileRepository repository;
    private final ModuleProfileMapper mapper;
    
    public List<ModuleProfileResponse> findAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::toResponse)
                         .collect(Collectors.toList());
    }

    public Optional<ModuleProfileResponse> findById(Long id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    public ModuleProfileResponse save(ModuleProfileRequest request) {
        ModuleProfile entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    public ModuleProfileResponse update(Long id, ModuleProfileRequest request) {
        ModuleProfile existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ModuleProfile not found with id " + id));
        
        mapper.updateFromDto(request, existingEntity);
        
        ModuleProfile savedEntity = repository.save(existingEntity);
        return mapper.toResponse(savedEntity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}