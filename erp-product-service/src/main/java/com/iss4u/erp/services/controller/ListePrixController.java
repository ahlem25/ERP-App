package com.iss4u.erp.services.controller;

import org.springframework.web.bind.annotation.*;
import com.iss4u.erp.services.service.ListePrixService;
import com.iss4u.erp.services.modules.achat.domain.common.dto.listeprix.request.ListePrixRequest;
import com.iss4u.erp.services.modules.achat.domain.common.dto.listeprix.response.ListePrixResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/listes-prix")
public class ListePrixController {

    private final ListePrixService service;

    @GetMapping
    public List<ListePrixResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<ListePrixResponse> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ListePrixResponse create(@RequestBody ListePrixRequest request) {
        return service.save(request);
    }

    @PutMapping("/{id}")
    public ListePrixResponse update(
        @PathVariable Long id,
        @RequestBody ListePrixRequest request
    ) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}