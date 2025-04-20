package com.iss4u.erp.services.controller;

import org.springframework.web.bind.annotation.*;
import com.iss4u.erp.services.service.DemandeMaterielService;
import com.iss4u.erp.services.modules.achat.domain.commande.dto.demandemateriel.request.DemandeMaterielRequest;
import com.iss4u.erp.services.modules.achat.domain.commande.dto.demandemateriel.response.DemandeMaterielResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/demandes-materiels")
public class DemandeMaterielController {

    private final DemandeMaterielService service;

    @GetMapping
    public List<DemandeMaterielResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<DemandeMaterielResponse> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public DemandeMaterielResponse create(@RequestBody DemandeMaterielRequest request) {
        return service.save(request);
    }

    @PutMapping("/{id}")
    public DemandeMaterielResponse update(
        @PathVariable Long id,
        @RequestBody DemandeMaterielRequest request
    ) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}