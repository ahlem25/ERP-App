package com.iss4u.erp.services.service;

import org.springframework.stereotype.Service;
import com.iss4u.erp.services.modules.achat.domain.commande.repository.TaxesFraisRepository;
import com.iss4u.erp.services.modules.achat.domain.commande.mapper.TaxesFraisMapper;
import com.iss4u.erp.services.modules.achat.domain.commande.models.TaxesFrais;
import com.iss4u.erp.services.modules.achat.domain.commande.dto.taxesfrais.request.TaxesFraisRequest;
import com.iss4u.erp.services.modules.achat.domain.commande.dto.taxesfrais.response.TaxesFraisResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaxesFraisService {

    private final TaxesFraisRepository repository;
    private final TaxesFraisMapper mapper;
    
    public List<TaxesFraisResponse> findAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::toResponse)
                         .collect(Collectors.toList());
    }

    public Optional<TaxesFraisResponse> findById(Long id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    public TaxesFraisResponse save(TaxesFraisRequest request) {
        TaxesFrais entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    public TaxesFraisResponse update(Long id, TaxesFraisRequest request) {
        TaxesFrais existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("TaxesFrais not found with id " + id));
        
        mapper.updateFromDto(request, existingEntity);
        
        TaxesFrais savedEntity = repository.save(existingEntity);
        return mapper.toResponse(savedEntity);
    }



    public void delete(Long id) {
        repository.deleteById(id);
    }
}