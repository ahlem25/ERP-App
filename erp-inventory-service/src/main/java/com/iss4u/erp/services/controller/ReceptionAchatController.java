package com.iss4u.erp.services.controller;

import org.springframework.web.bind.annotation.*;
import com.iss4u.erp.services.service.ReceptionAchatService;
import com.iss4u.erp.services.modules.stock.domain.dto.receptionachat.request.ReceptionAchatRequest;
import com.iss4u.erp.services.modules.stock.domain.dto.receptionachat.response.ReceptionAchatResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/receptions-achat")
public class ReceptionAchatController {

    private final ReceptionAchatService service;

    @GetMapping
    public List<ReceptionAchatResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<ReceptionAchatResponse> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ReceptionAchatResponse create(@RequestBody ReceptionAchatRequest request) {
        return service.save(request);
    }

    @PutMapping("/{id}")
    public ReceptionAchatResponse update(
        @PathVariable Long id,
        @RequestBody ReceptionAchatRequest request
    ) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}