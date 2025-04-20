package com.iss4u.erp.services.service;

import org.springframework.stereotype.Service;
import com.iss4u.erp.services.modules.stock.domain.repository.BonLivraisonRepository;
import com.iss4u.erp.services.modules.stock.domain.mapper.BonLivraisonMapper;
import com.iss4u.erp.services.modules.stock.domain.models.BonLivraison;
import com.iss4u.erp.services.modules.stock.domain.dto.bonlivraison.request.BonLivraisonRequest;
import com.iss4u.erp.services.modules.stock.domain.dto.bonlivraison.response.BonLivraisonResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BonLivraisonService {

    private final BonLivraisonRepository repository;
    private final BonLivraisonMapper mapper;
    
    public List<BonLivraisonResponse> findAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::toResponse)
                         .collect(Collectors.toList());
    }

    public Optional<BonLivraisonResponse> findById(Long id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    public BonLivraisonResponse save(BonLivraisonRequest request) {
        BonLivraison entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    public BonLivraisonResponse update(Long id, BonLivraisonRequest request) {
        BonLivraison existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("BonLivraison not found with id " + id));
        
        mapper.updateFromDto(request, existingEntity);
        
        BonLivraison savedEntity = repository.save(existingEntity);
        return mapper.toResponse(savedEntity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}