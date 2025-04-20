package com.iss4u.erp.services.controller;

import org.springframework.web.bind.annotation.*;
import com.iss4u.erp.services.service.UniteMesureService;
import com.iss4u.erp.services.modules.stock.domain.dto.unitemesure.request.UniteMesureRequest;
import com.iss4u.erp.services.modules.stock.domain.dto.unitemesure.response.UniteMesureResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/unites-mesure")
public class UniteMesureController {

    private final UniteMesureService service;

    @GetMapping
    public List<UniteMesureResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<UniteMesureResponse> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public UniteMesureResponse create(@RequestBody UniteMesureRequest request) {
        return service.save(request);
    }

    @PutMapping("/{id}")
    public UniteMesureResponse update(
        @PathVariable Long id,
        @RequestBody UniteMesureRequest request
    ) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}