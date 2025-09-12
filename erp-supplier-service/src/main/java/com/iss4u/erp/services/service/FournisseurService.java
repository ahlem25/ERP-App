package com.iss4u.erp.services.service;

import org.springframework.stereotype.Service;
import com.iss4u.erp.services.modules.achat.domain.fournisseur.repository.FournisseurRepository;
import com.iss4u.erp.services.modules.achat.domain.fournisseur.mapper.FournisseurMapper;
import com.iss4u.erp.services.modules.achat.domain.fournisseur.models.Fournisseur;
import com.iss4u.erp.services.modules.achat.domain.fournisseur.dto.fournisseur.request.FournisseurRequest;
import com.iss4u.erp.services.modules.achat.domain.fournisseur.dto.fournisseur.response.FournisseurResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FournisseurService {
    private final FournisseurRepository repository;
    private final FournisseurMapper mapper;
    
    public List<FournisseurResponse> findAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::toResponse)
                         .collect(Collectors.toList());
    }

    public Optional<FournisseurResponse> findById(Long id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    public FournisseurResponse save(FournisseurRequest request) {
        Fournisseur entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    public FournisseurResponse update(Long id, FournisseurRequest request) {
        Fournisseur existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fournisseur not found with id " + id));
        
        mapper.updateFromDto(request, existingEntity);
        
        Fournisseur savedEntity = repository.save(existingEntity);
        return mapper.toResponse(savedEntity);
    }


    public void delete(Long id) {
        repository.deleteById(id);
    }
}