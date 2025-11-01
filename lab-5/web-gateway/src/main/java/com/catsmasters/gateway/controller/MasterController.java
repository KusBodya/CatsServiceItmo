package com.catsmasters.gateway.controller;

import com.catsmasters.gateway.dto.MasterDto;
import com.catsmasters.gateway.service.MasterOrchestrationService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/masters")
public class MasterController {

    private final MasterOrchestrationService masterService;

    public MasterController(MasterOrchestrationService masterService) {
        this.masterService = masterService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<MasterDto>> getAllMasters() {
        return ResponseEntity.ok(masterService.getAllMasters());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MasterDto> getMasterById(@PathVariable Long id) {
        return ResponseEntity.ok(masterService.getMasterById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MasterDto> createMaster(@RequestBody MasterDto masterDto) {
        return ResponseEntity.ok(masterService.createMaster(masterDto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MasterDto> updateMaster(@PathVariable Long id, @RequestBody MasterDto masterDto) {
        return ResponseEntity.ok(masterService.updateMaster(id, masterDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteMaster(@PathVariable Long id) {
        masterService.deleteMaster(id);
        return ResponseEntity.noContent().build();
    }
}