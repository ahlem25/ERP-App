package com.iss4u.erp.services.controller;

import org.springframework.web.bind.annotation.*;
import com.iss4u.erp.services.service.NumeroSerieService;
import com.iss4u.erp.services.modules.stock.domain.dto.numeroserie.request.NumeroSerieRequest;
import com.iss4u.erp.services.modules.stock.domain.dto.numeroserie.response.NumeroSerieResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/numeros-serie")
public class NumeroSerieController {

    private final NumeroSerieService service;

    @GetMapping
    public List<NumeroSerieResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<NumeroSerieResponse> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public NumeroSerieResponse create(@RequestBody NumeroSerieRequest request) {
        return service.save(request);
    }

    @PutMapping("/{id}")
    public NumeroSerieResponse update(
        @PathVariable Long id,
        @RequestBody NumeroSerieRequest request
    ) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}