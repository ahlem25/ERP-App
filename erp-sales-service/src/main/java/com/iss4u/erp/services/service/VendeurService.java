package com.iss4u.erp.services.service;

import org.springframework.stereotype.Service;
import com.iss4u.erp.services.modules.vente.domain.sales.repository.VendeurRepository;
import com.iss4u.erp.services.modules.vente.domain.sales.mapper.VendeurMapper;
import com.iss4u.erp.services.modules.vente.domain.sales.models.Vendeur;
import com.iss4u.erp.services.modules.vente.domain.sales.dto.vendeur.request.VendeurRequest;
import com.iss4u.erp.services.modules.vente.domain.sales.dto.vendeur.response.VendeurResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VendeurService {

    private final VendeurRepository repository;
    private final VendeurMapper mapper;

    public List<VendeurResponse> findAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::toResponse)
                         .collect(Collectors.toList());
    }

    public Optional<VendeurResponse> findById(Long id) {
        return repository.findById(id).map(mapper::toResponse);
    }

    public VendeurResponse save(VendeurRequest request) {
        Vendeur entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    public VendeurResponse update(Long id, VendeurRequest request) {
        Vendeur existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vendeur not found with id " + id));
        
        mapper.updateFromDto(request, existingEntity);
        
        Vendeur savedEntity = repository.save(existingEntity);
        return mapper.toResponse(savedEntity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}