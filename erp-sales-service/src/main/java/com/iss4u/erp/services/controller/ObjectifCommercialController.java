package com.iss4u.erp.services.controller;

import org.springframework.web.bind.annotation.*;
import com.iss4u.erp.services.service.ObjectifCommercialService;
import com.iss4u.erp.services.modules.vente.domain.sales.dto.objectifcommercial.request.ObjectifCommercialRequest;
import com.iss4u.erp.services.modules.vente.domain.sales.dto.objectifcommercial.response.ObjectifCommercialResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor

@RequestMapping("/api/v1/objectifs-commercial")
public class ObjectifCommercialController {

    private final ObjectifCommercialService service;

    @GetMapping
    public List<ObjectifCommercialResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<ObjectifCommercialResponse> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ObjectifCommercialResponse create(@RequestBody ObjectifCommercialRequest request) {
        return service.save(request);
    }

    @PutMapping("/{id}")
    public ObjectifCommercialResponse update(
        @PathVariable Long id,
        @RequestBody ObjectifCommercialRequest request
    ) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}