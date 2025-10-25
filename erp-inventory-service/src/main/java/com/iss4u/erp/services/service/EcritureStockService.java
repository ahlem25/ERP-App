package com.iss4u.erp.services.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.iss4u.erp.services.modules.stock.domain.repository.EcritureStockRepository;
import com.iss4u.erp.services.modules.stock.domain.mapper.EcritureStockMapper;
import com.iss4u.erp.services.modules.stock.domain.models.EcritureStock;
import com.iss4u.erp.services.modules.stock.domain.dto.ecriturestock.request.EcritureStockRequest;
import com.iss4u.erp.services.modules.stock.domain.dto.ecriturestock.response.EcritureStockResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EcritureStockService {
    private final EcritureStockRepository repository;
    private final EcritureStockMapper mapper;
    
    public List<EcritureStockResponse> findAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::toResponse)
                         .collect(Collectors.toList());
    }

    public Optional<EcritureStockResponse> findById(Long id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    public EcritureStockResponse save(EcritureStockRequest request) {
        EcritureStock entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    @Transactional
    public EcritureStockResponse update(Long id, EcritureStockRequest request) {
        EcritureStock existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("EcritureStock not found with id " + id));
        
        // Mise à jour manuelle des champs pour éviter les problèmes de cascade
        existingEntity.setTypeEntree(request.getTypeEntree());
        existingEntity.setQuantites(request.getQuantites());
        
        // Mettre à jour les relations si elles sont fournies
        if (request.getArticle() != null) {
            existingEntity.setArticle(request.getArticle());
        }
        if (request.getEntrepotSource() != null) {
            existingEntity.setEntrepotSource(request.getEntrepotSource());
        }
        if (request.getEntrepotCible() != null) {
            existingEntity.setEntrepotCible(request.getEntrepotCible());
        }
        if (request.getUnitesMesure() != null) {
            existingEntity.setUnitesMesure(request.getUnitesMesure());
        }
        
        EcritureStock savedEntity = repository.save(existingEntity);
        return mapper.toResponse(savedEntity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}