package com.iss4u.erp.services.controller;

import org.springframework.web.bind.annotation.*;
import com.iss4u.erp.services.service.VendeurService;
import com.iss4u.erp.services.modules.vente.domain.sales.dto.vendeur.request.VendeurRequest;
import com.iss4u.erp.services.modules.vente.domain.sales.dto.vendeur.response.VendeurResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/vendeur")
public class VendeurController {

    private final VendeurService service;

    @GetMapping
    public List<VendeurResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<VendeurResponse> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public VendeurResponse create(@RequestBody VendeurRequest request) {
        return service.save(request);
    }

    @PutMapping("/{id}")
    public VendeurResponse update(
        @PathVariable Long id,
        @RequestBody VendeurRequest request
    ) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}