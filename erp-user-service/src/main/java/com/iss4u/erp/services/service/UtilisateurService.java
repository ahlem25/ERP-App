package com.iss4u.erp.services.service;

import org.springframework.stereotype.Service;
import com.iss4u.erp.services.modules.core.domain.repository.UtilisateurRepository;
import com.iss4u.erp.services.modules.core.domain.mapper.UtilisateurMapper;
import com.iss4u.erp.services.modules.core.domain.models.Utilisateur;
import com.iss4u.erp.services.modules.core.domain.dto.utilisateur.request.UtilisateurRequest;
import com.iss4u.erp.services.modules.core.domain.dto.utilisateur.response.UtilisateurResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UtilisateurService {

    private final UtilisateurRepository repository;
    private final UtilisateurMapper mapper;
    
    public List<UtilisateurResponse> findAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::toResponse)
                         .collect(Collectors.toList());
    }

    public Optional<UtilisateurResponse> findById(Long id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    public UtilisateurResponse save(UtilisateurRequest request) {
        Utilisateur entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    public UtilisateurResponse update(Long id, UtilisateurRequest request) {
        Utilisateur existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur not found with id " + id));
        
        mapper.updateFromDto(request, existingEntity);
        
        Utilisateur savedEntity = repository.save(existingEntity);
        return mapper.toResponse(savedEntity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}