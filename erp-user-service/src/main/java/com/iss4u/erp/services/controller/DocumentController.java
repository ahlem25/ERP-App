package com.iss4u.erp.services.controller;

import org.springframework.web.bind.annotation.*;
import com.iss4u.erp.services.service.DocumentService;
import com.iss4u.erp.services.modules.core.domain.dto.document.request.DocumentRequest;
import com.iss4u.erp.services.modules.core.domain.dto.document.response.DocumentResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/documents")
public class DocumentController {

    private final DocumentService service;

    @GetMapping
    public List<DocumentResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<DocumentResponse> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public DocumentResponse create(@RequestBody DocumentRequest request) {
        return service.save(request);
    }

    @PutMapping("/{id}")
    public DocumentResponse update(
        @PathVariable Long id,
        @RequestBody DocumentRequest request
    ) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}