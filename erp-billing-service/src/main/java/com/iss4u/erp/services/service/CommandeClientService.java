package com.iss4u.erp.services.service;

import org.springframework.stereotype.Service;
import com.iss4u.erp.services.modules.vente.domain.billing.repository.CommandeClientRepository;
import com.iss4u.erp.services.modules.vente.domain.billing.mapper.CommandeClientMapper;
import com.iss4u.erp.services.modules.vente.domain.billing.models.CommandeClient;
import com.iss4u.erp.services.modules.vente.domain.billing.dto.commandeclient.request.CommandeClientRequest;
import com.iss4u.erp.services.modules.vente.domain.billing.dto.commandeclient.response.CommandeClientResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class CommandeClientService {

    private final CommandeClientRepository repository;
    private final CommandeClientMapper mapper;
    
    public List<CommandeClientResponse> findAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::toResponse)
                         .collect(Collectors.toList());
    }

    public Optional<CommandeClientResponse> findById(Long id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    public CommandeClientResponse save(CommandeClientRequest request) {
        CommandeClient entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    public CommandeClientResponse update(Long id, CommandeClientRequest request) {
        CommandeClient existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("CommandeClient not found with id " + id));
        
        mapper.updateFromDto(request, existingEntity);
        
        CommandeClient savedEntity = repository.save(existingEntity);
        return mapper.toResponse(savedEntity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}