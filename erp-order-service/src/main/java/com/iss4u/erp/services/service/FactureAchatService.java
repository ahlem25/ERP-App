package com.iss4u.erp.services.service;

import org.springframework.stereotype.Service;
import com.iss4u.erp.services.modules.achat.domain.commande.repository.FactureAchatRepository;
import com.iss4u.erp.services.modules.achat.domain.commande.mapper.FactureAchatMapper;
import com.iss4u.erp.services.modules.achat.domain.commande.models.FactureAchat;
import com.iss4u.erp.services.modules.achat.domain.commande.dto.factureachat.request.FactureAchatRequest;
import com.iss4u.erp.services.modules.achat.domain.commande.dto.factureachat.response.FactureAchatResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FactureAchatService {

    private final FactureAchatRepository repository;
    private final FactureAchatMapper mapper;
    
    public List<FactureAchatResponse> findAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::toResponse)
                         .collect(Collectors.toList());
    }

    public Optional<FactureAchatResponse> findById(Long id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    public FactureAchatResponse save(FactureAchatRequest request) {
        FactureAchat entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    public FactureAchatResponse update(Long id, FactureAchatRequest request) {
        FactureAchat existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("FactureAchat not found with id " + id));
        
        mapper.updateFromDto(request, existingEntity);
        
        FactureAchat savedEntity = repository.save(existingEntity);
        return mapper.toResponse(savedEntity);
    }



    public void delete(Long id) {
        repository.deleteById(id);
    }
}