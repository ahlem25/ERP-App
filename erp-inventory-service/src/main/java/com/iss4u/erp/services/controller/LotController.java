package com.iss4u.erp.services.controller;

import org.springframework.web.bind.annotation.*;
import com.iss4u.erp.services.service.LotService;
import com.iss4u.erp.services.modules.stock.domain.dto.lot.request.LotRequest;
import com.iss4u.erp.services.modules.stock.domain.dto.lot.response.LotResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/lots")
public class LotController {

    private final LotService service;

    @GetMapping
    public List<LotResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<LotResponse> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public LotResponse create(@RequestBody LotRequest request) {
        return service.save(request);
    }

    @PutMapping("/{id}")
    public LotResponse update(
        @PathVariable Long id,
        @RequestBody LotRequest request
    ) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}