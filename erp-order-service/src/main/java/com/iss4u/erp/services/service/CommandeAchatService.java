package com.iss4u.erp.services.service;

import org.springframework.stereotype.Service;
import com.iss4u.erp.services.modules.achat.domain.commande.repository.CommandeAchatRepository;
import com.iss4u.erp.services.modules.achat.domain.commande.mapper.CommandeAchatMapper;
import com.iss4u.erp.services.modules.achat.domain.commande.models.CommandeAchat;
import com.iss4u.erp.services.modules.achat.domain.commande.dto.commandeachat.request.CommandeAchatRequest;
import com.iss4u.erp.services.modules.achat.domain.commande.dto.commandeachat.response.CommandeAchatResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommandeAchatService {

    private final CommandeAchatRepository repository;
    private final CommandeAchatMapper mapper;
    
    public List<CommandeAchatResponse> findAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::toResponse)
                         .collect(Collectors.toList());
    }

    public Optional<CommandeAchatResponse> findById(Long id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    public CommandeAchatResponse save(CommandeAchatRequest request) {
        CommandeAchat entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    public CommandeAchatResponse update(Long id, CommandeAchatRequest request) {
        CommandeAchat existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("CommandeAchat not found with id " + id));
        
        mapper.updateFromDto(request, existingEntity);
        
        CommandeAchat savedEntity = repository.save(existingEntity);
        return mapper.toResponse(savedEntity);
    }



    public void delete(Long id) {
        repository.deleteById(id);
    }
}