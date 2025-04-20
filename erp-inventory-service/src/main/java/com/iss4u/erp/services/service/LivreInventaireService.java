package com.iss4u.erp.services.service;

import org.springframework.stereotype.Service;
import com.iss4u.erp.services.modules.stock.domain.repository.LivreInventaireRepository;
import com.iss4u.erp.services.modules.stock.domain.mapper.LivreInventaireMapper;
import com.iss4u.erp.services.modules.stock.domain.models.LivreInventaire;
import com.iss4u.erp.services.modules.stock.domain.dto.livreinventaire.request.LivreInventaireRequest;
import com.iss4u.erp.services.modules.stock.domain.dto.livreinventaire.response.LivreInventaireResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LivreInventaireService {

    private final LivreInventaireRepository repository;
    private final LivreInventaireMapper mapper;
    
    public List<LivreInventaireResponse> findAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::toResponse)
                         .collect(Collectors.toList());
    }

    public Optional<LivreInventaireResponse> findById(Long id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    public LivreInventaireResponse save(LivreInventaireRequest request) {
        LivreInventaire entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    public LivreInventaireResponse update(Long id, LivreInventaireRequest request) {
        LivreInventaire existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("LivreInventaire not found with id " + id));
        
        mapper.updateFromDto(request, existingEntity);
        
        LivreInventaire savedEntity = repository.save(existingEntity);
        return mapper.toResponse(savedEntity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}