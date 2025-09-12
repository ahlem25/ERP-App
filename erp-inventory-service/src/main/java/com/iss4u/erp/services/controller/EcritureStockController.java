package com.iss4u.erp.services.controller;

import org.springframework.web.bind.annotation.*;
import com.iss4u.erp.services.service.EcritureStockService;
import com.iss4u.erp.services.modules.stock.domain.dto.ecriturestock.request.EcritureStockRequest;
import com.iss4u.erp.services.modules.stock.domain.dto.ecriturestock.response.EcritureStockResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ecritures-stock")
public class EcritureStockController {

    private final EcritureStockService service;

    @GetMapping
    public List<EcritureStockResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<EcritureStockResponse> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public EcritureStockResponse create(@RequestBody EcritureStockRequest request) {
        return service.save(request);
    }

    @PutMapping("/{id}")
    public EcritureStockResponse update(
        @PathVariable Long id,
        @RequestBody EcritureStockRequest request
    ) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}