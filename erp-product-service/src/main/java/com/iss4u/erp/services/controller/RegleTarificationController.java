package com.iss4u.erp.services.controller;

import org.springframework.web.bind.annotation.*;
import com.iss4u.erp.services.service.RegleTarificationService;
import com.iss4u.erp.services.modules.achat.domain.common.dto.regletarification.request.RegleTarificationRequest;
import com.iss4u.erp.services.modules.achat.domain.common.dto.regletarification.response.RegleTarificationResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/regles-tarifications")
public class RegleTarificationController {

    private final RegleTarificationService service;

    

    @GetMapping
    public List<RegleTarificationResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<RegleTarificationResponse> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public RegleTarificationResponse create(@RequestBody RegleTarificationRequest request) {
        return service.save(request);
    }

    @PutMapping("/{id}")
    public RegleTarificationResponse update(
        @PathVariable Long id,
        @RequestBody RegleTarificationRequest request
    ) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}