package com.iss4u.erp.services.controller;

import org.springframework.web.bind.annotation.*;
import com.iss4u.erp.services.service.SoldeStockService;
import com.iss4u.erp.services.modules.stock.domain.dto.soldestock.request.SoldeStockRequest;
import com.iss4u.erp.services.modules.stock.domain.dto.soldestock.response.SoldeStockResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/soldes-stock")
public class SoldeStockController {

    private final SoldeStockService service;

    @GetMapping
    public List<SoldeStockResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<SoldeStockResponse> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public SoldeStockResponse create(@RequestBody SoldeStockRequest request) {
        return service.save(request);
    }

    @PutMapping("/{id}")
    public SoldeStockResponse update(
        @PathVariable Long id,
        @RequestBody SoldeStockRequest request
    ) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}