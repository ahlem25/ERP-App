package com.iss4u.erp.services.service;

import org.springframework.stereotype.Service;
import com.iss4u.erp.services.modules.core.domain.repository.ProfilRoleRepository;
import com.iss4u.erp.services.modules.core.domain.mapper.ProfilRoleMapper;
import com.iss4u.erp.services.modules.core.domain.models.ProfilRole;
import com.iss4u.erp.services.modules.core.domain.dto.profilrole.request.ProfilRoleRequest;
import com.iss4u.erp.services.modules.core.domain.dto.profilrole.response.ProfilRoleResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfilRoleService {

    private final ProfilRoleRepository repository;
    private final ProfilRoleMapper mapper;
    
    public List<ProfilRoleResponse> findAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::toResponse)
                         .collect(Collectors.toList());
    }

    public Optional<ProfilRoleResponse> findById(Long id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    public ProfilRoleResponse save(ProfilRoleRequest request) {
        ProfilRole entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    public ProfilRoleResponse update(Long id, ProfilRoleRequest request) {
        ProfilRole existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ProfilRole not found with id " + id));
        
        mapper.updateFromDto(request, existingEntity);
        
        ProfilRole savedEntity = repository.save(existingEntity);
        return mapper.toResponse(savedEntity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}