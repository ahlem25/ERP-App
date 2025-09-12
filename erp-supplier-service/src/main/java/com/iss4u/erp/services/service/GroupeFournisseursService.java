package com.iss4u.erp.services.service;

import org.springframework.stereotype.Service;
import com.iss4u.erp.services.modules.achat.domain.fournisseur.repository.GroupeFournisseursRepository;
import com.iss4u.erp.services.modules.achat.domain.fournisseur.mapper.GroupeFournisseursMapper;
import com.iss4u.erp.services.modules.achat.domain.fournisseur.models.GroupeFournisseurs;
import com.iss4u.erp.services.modules.achat.domain.fournisseur.dto.groupefournisseurs.request.GroupeFournisseursRequest;
import com.iss4u.erp.services.modules.achat.domain.fournisseur.dto.groupefournisseurs.response.GroupeFournisseursResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class GroupeFournisseursService {
    private final GroupeFournisseursRepository repository;
    private final GroupeFournisseursMapper mapper;
    
    public List<GroupeFournisseursResponse> findAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::toResponse)
                         .collect(Collectors.toList());
    }

    public Optional<GroupeFournisseursResponse> findById(Long id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    public GroupeFournisseursResponse save(GroupeFournisseursRequest request) {
        GroupeFournisseurs entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    public GroupeFournisseursResponse update(Long id, GroupeFournisseursRequest request) {
        GroupeFournisseurs existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("GroupeFournisseurs not found with id " + id));
        
        mapper.updateFromDto(request, existingEntity);
        
        GroupeFournisseurs savedEntity = repository.save(existingEntity);
        return mapper.toResponse(savedEntity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}