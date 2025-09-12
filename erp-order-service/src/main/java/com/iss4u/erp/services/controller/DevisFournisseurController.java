package com.iss4u.erp.services.controller;

import org.springframework.web.bind.annotation.*;
import com.iss4u.erp.services.service.DevisFournisseurService;
import com.iss4u.erp.services.modules.achat.domain.commande.dto.devisfournisseur.request.DevisFournisseurRequest;
import com.iss4u.erp.services.modules.achat.domain.commande.dto.devisfournisseur.response.DevisFournisseurResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/devis-fournisseurs")
public class DevisFournisseurController {

    private final DevisFournisseurService service;

    @GetMapping
    public List<DevisFournisseurResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<DevisFournisseurResponse> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public DevisFournisseurResponse create(@RequestBody DevisFournisseurRequest request) {
        return service.save(request);
    }

    @PutMapping("/{id}")
    public DevisFournisseurResponse update(
        @PathVariable Long id,
        @RequestBody DevisFournisseurRequest request
    ) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}