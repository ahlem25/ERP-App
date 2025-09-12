package com.iss4u.erp.services.controller;

import org.springframework.web.bind.annotation.*;
import com.iss4u.erp.services.service.FactureVenteService;
import com.iss4u.erp.services.modules.vente.domain.billing.dto.facturevente.request.FactureVenteRequest;
import com.iss4u.erp.services.modules.vente.domain.billing.dto.facturevente.response.FactureVenteResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/factures-vente")
public class FactureVenteController {

    private final FactureVenteService service;

    @GetMapping
    public List<FactureVenteResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<FactureVenteResponse> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public FactureVenteResponse create(@RequestBody FactureVenteRequest request) {
        return service.save(request);
    }

    @PutMapping("/{id}")
    public FactureVenteResponse update(
        @PathVariable Long id,
        @RequestBody FactureVenteRequest request
    ) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}