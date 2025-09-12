package com.iss4u.erp.services.controller;

import org.springframework.web.bind.annotation.*;
import com.iss4u.erp.services.service.TaxeService;
import com.iss4u.erp.services.modules.vente.domain.sales.dto.taxe.request.TaxeRequest;
import com.iss4u.erp.services.modules.vente.domain.sales.dto.taxe.response.TaxeResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/taxes")
public class TaxeController {

    private final TaxeService service;

    @GetMapping
    public List<TaxeResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<TaxeResponse> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public TaxeResponse create(@RequestBody TaxeRequest request) {
        return service.save(request);
    }

    @PutMapping("/{id}")
    public TaxeResponse update(
        @PathVariable Long id,
        @RequestBody TaxeRequest request
    ) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}