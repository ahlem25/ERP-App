package com.iss4u.erp.services.controller;

import org.springframework.web.bind.annotation.*;
import com.iss4u.erp.services.service.DevisService;
import com.iss4u.erp.services.modules.vente.domain.billing.dto.devis.request.DevisRequest;
import com.iss4u.erp.services.modules.vente.domain.billing.dto.devis.response.DevisResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/devis")
public class DevisController {

    private final DevisService service;

    @GetMapping
    public List<DevisResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<DevisResponse> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public DevisResponse create(@RequestBody DevisRequest request) {
        return service.save(request);
    }

    @PutMapping("/{id}")
    public DevisResponse update(
        @PathVariable Long id,
        @RequestBody DevisRequest request
    ) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}