package com.iss4u.erp.services.controller;

import org.springframework.web.bind.annotation.*;
import com.iss4u.erp.services.service.PaiementService;
import com.iss4u.erp.services.modules.vente.domain.payment.dto.paiement.request.PaiementRequest;
import com.iss4u.erp.services.modules.vente.domain.payment.dto.paiement.response.PaiementResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/paiements")
public class PaiementController {

    private final PaiementService service;

    @GetMapping
    public List<PaiementResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<PaiementResponse> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public PaiementResponse create(@RequestBody PaiementRequest request) {
        return service.save(request);
    }

    @PutMapping("/{id}")
    public PaiementResponse update(
        @PathVariable Long id,
        @RequestBody PaiementRequest request
    ) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}