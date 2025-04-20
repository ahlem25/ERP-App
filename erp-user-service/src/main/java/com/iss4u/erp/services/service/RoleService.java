package com.iss4u.erp.services.service;

import org.springframework.stereotype.Service;
import com.iss4u.erp.services.modules.core.domain.repository.RoleRepository;
import com.iss4u.erp.services.modules.core.domain.mapper.RoleMapper;
import com.iss4u.erp.services.modules.core.domain.models.Role;
import com.iss4u.erp.services.modules.core.domain.dto.role.request.RoleRequest;
import com.iss4u.erp.services.modules.core.domain.dto.role.response.RoleResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository repository;
    private final RoleMapper mapper;
    
    public List<RoleResponse> findAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::toResponse)
                         .collect(Collectors.toList());
    }

    public Optional<RoleResponse> findById(Long id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    public RoleResponse save(RoleRequest request) {
        Role entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    public RoleResponse update(Long id, RoleRequest request) {
        Role existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found with id " + id));
        
        mapper.updateFromDto(request, existingEntity);
        
        Role savedEntity = repository.save(existingEntity);
        return mapper.toResponse(savedEntity);
    }



    public void delete(Long id) {
        repository.deleteById(id);
    }
}