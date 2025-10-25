package com.iss4u.erp.services.service;

import org.springframework.stereotype.Service;
import com.iss4u.erp.services.modules.achat.domain.commande.repository.DevisFournisseurRepository;
import com.iss4u.erp.services.modules.achat.domain.commande.mapper.DevisFournisseurMapper;
import com.iss4u.erp.services.modules.achat.domain.commande.models.DevisFournisseur;
import com.iss4u.erp.services.modules.achat.domain.commande.dto.devisfournisseur.request.DevisFournisseurRequest;
import com.iss4u.erp.services.modules.achat.domain.commande.dto.devisfournisseur.response.DevisFournisseurResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DevisFournisseurService {

    private final DevisFournisseurRepository repository;
    private final DevisFournisseurMapper mapper;
    
    public List<DevisFournisseurResponse> findAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::toResponse)
                         .collect(Collectors.toList());
    }

    public Optional<DevisFournisseurResponse> findById(Long id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    public DevisFournisseurResponse save(DevisFournisseurRequest request) {
        DevisFournisseur entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    public DevisFournisseurResponse update(Long id, DevisFournisseurRequest request) {
        DevisFournisseur existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("DevisFournisseur not found with id " + id));
        
        mapper.updateFromDto(request, existingEntity);
        
        DevisFournisseur savedEntity = repository.save(existingEntity);
        return mapper.toResponse(savedEntity);
    }



    public void delete(Long id) {
        repository.deleteById(id);
    }
}