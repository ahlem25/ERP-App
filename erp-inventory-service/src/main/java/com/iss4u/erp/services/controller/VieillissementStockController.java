package com.iss4u.erp.services.controller;

import org.springframework.web.bind.annotation.*;
import com.iss4u.erp.services.service.VieillissementStockService;
import com.iss4u.erp.services.modules.stock.domain.dto.vieillissementstock.request.VieillissementStockRequest;
import com.iss4u.erp.services.modules.stock.domain.dto.vieillissementstock.response.VieillissementStockResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/vieillissement-stocks")
public class VieillissementStockController {

    private final VieillissementStockService service;

    @GetMapping
    public List<VieillissementStockResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<VieillissementStockResponse> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public VieillissementStockResponse create(@RequestBody VieillissementStockRequest request) {
        return service.save(request);
    }

    @PutMapping("/{id}")
    public VieillissementStockResponse update(
        @PathVariable Long id,
        @RequestBody VieillissementStockRequest request
    ) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}