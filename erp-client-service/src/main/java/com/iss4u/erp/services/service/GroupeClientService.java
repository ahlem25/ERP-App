package com.iss4u.erp.services.service;

import org.springframework.stereotype.Service;
import com.iss4u.erp.services.modules.vente.domain.client.repository.GroupeClientRepository;
import com.iss4u.erp.services.modules.vente.domain.client.mapper.GroupeClientMapper;
import com.iss4u.erp.services.modules.vente.domain.client.models.GroupeClient;
import com.iss4u.erp.services.modules.vente.domain.client.dto.groupeclient.request.GroupeClientRequest;
import com.iss4u.erp.services.modules.vente.domain.client.dto.groupeclient.response.GroupeClientResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GroupeClientService {

    private final GroupeClientRepository repository;
    private final GroupeClientMapper mapper;
    
    public List<GroupeClientResponse> findAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::toResponse)
                         .collect(Collectors.toList());
    }

    public Optional<GroupeClientResponse> findById(Long id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    public GroupeClientResponse save(GroupeClientRequest request) {
        GroupeClient entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    public GroupeClientResponse update(Long id, GroupeClientRequest request) {
        GroupeClient existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("GroupeClient not found with id " + id));
        
        mapper.updateFromDto(request, existingEntity);
        
        GroupeClient savedEntity = repository.save(existingEntity);
        return mapper.toResponse(savedEntity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
