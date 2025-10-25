package com.iss4u.erp.services.service;

import org.springframework.stereotype.Service;
import com.iss4u.erp.services.modules.vente.domain.client.repository.ClientRepository;
import com.iss4u.erp.services.modules.vente.domain.client.repository.GroupeClientRepository;
import com.iss4u.erp.services.modules.vente.domain.client.mapper.ClientMapper;
import com.iss4u.erp.services.modules.vente.domain.client.models.Client;
import com.iss4u.erp.services.modules.vente.domain.client.models.GroupeClient;
import com.iss4u.erp.services.modules.vente.domain.client.dto.client.request.ClientRequest;
import com.iss4u.erp.services.modules.vente.domain.client.dto.client.response.ClientResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository repository;
    private final GroupeClientRepository groupeClientRepository;
    private final ClientMapper mapper;
    
    public List<ClientResponse> findAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::toResponse)
                         .collect(Collectors.toList());
    }

    public Optional<ClientResponse> findById(Long id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    public ClientResponse save(ClientRequest request) {
        Client entity = mapper.toEntity(request);
        
        // Set the groupe relationship if groupeId is provided
        if (request.getGroupeId() != null) {
            GroupeClient groupe = groupeClientRepository.findById(request.getGroupeId())
                    .orElseThrow(() -> new RuntimeException("GroupeClient not found with id " + request.getGroupeId()));
            entity.setGroupe(groupe);
        }
        
        return mapper.toResponse(repository.save(entity));
    }

    public ClientResponse update(Long id, ClientRequest request) {
        Client existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found with id " + id));
        
        // Initialize collections if they are null to avoid orphanRemoval issues
        if (existingEntity.getCommandes() == null) {
            existingEntity.setCommandes(new java.util.ArrayList<>());
        }
        if (existingEntity.getDevis() == null) {
            existingEntity.setDevis(new java.util.ArrayList<>());
        }
        
        mapper.updateFromDto(request, existingEntity);
        
        // Update the groupe relationship if groupeId is provided
        if (request.getGroupeId() != null) {
            GroupeClient groupe = groupeClientRepository.findById(request.getGroupeId())
                    .orElseThrow(() -> new RuntimeException("GroupeClient not found with id " + request.getGroupeId()));
            existingEntity.setGroupe(groupe);
        } else {
            existingEntity.setGroupe(null);
        }
        
        Client savedEntity = repository.save(existingEntity);
        return mapper.toResponse(savedEntity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
