package com.iss4u.erp.services.controller;

import org.springframework.web.bind.annotation.*;
import com.iss4u.erp.services.service.ModuleProfileService;
import com.iss4u.erp.services.modules.core.domain.dto.moduleprofile.request.ModuleProfileRequest;
import com.iss4u.erp.services.modules.core.domain.dto.moduleprofile.response.ModuleProfileResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/modules-profile")
public class ModuleProfileController {

    private final ModuleProfileService service;

    @GetMapping
    public List<ModuleProfileResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<ModuleProfileResponse> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ModuleProfileResponse create(@RequestBody ModuleProfileRequest request) {
        return service.save(request);
    }

    @PutMapping("/{id}")
    public ModuleProfileResponse update(
        @PathVariable Long id,
        @RequestBody ModuleProfileRequest request
    ) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}