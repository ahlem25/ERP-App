package com.iss4u.erp.services.service;

import org.springframework.stereotype.Service;
import com.iss4u.erp.services.modules.achat.domain.commande.repository.AppelOffreRepository;
import com.iss4u.erp.services.modules.achat.domain.commande.mapper.AppelOffreMapper;
import com.iss4u.erp.services.modules.achat.domain.commande.models.AppelOffre;
import com.iss4u.erp.services.modules.achat.domain.commande.dto.appeloffre.request.AppelOffreRequest;
import com.iss4u.erp.services.modules.achat.domain.commande.dto.appeloffre.response.AppelOffreResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppelOffreService {

    private final AppelOffreRepository repository;
    private final AppelOffreMapper mapper;
    
    public List<AppelOffreResponse> findAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::toResponse)
                         .collect(Collectors.toList());
    }

    public Optional<AppelOffreResponse> findById(Long id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    public AppelOffreResponse save(AppelOffreRequest request) {
        AppelOffre entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    public AppelOffreResponse update(Long id, AppelOffreRequest request) {
        AppelOffre existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("AppelOffre not found with id " + id));
        
        mapper.updateFromDto(request, existingEntity);
        
        AppelOffre savedEntity = repository.save(existingEntity);
        return mapper.toResponse(savedEntity);
    }



    public void delete(Long id) {
        repository.deleteById(id);
    }
}