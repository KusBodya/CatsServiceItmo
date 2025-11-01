package com.catsmasters.masterservice.controller;

import com.catsmasters.masterservice.dto.MasterDto;
import com.catsmasters.masterservice.service.MasterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/masters")
public class MasterController {

    private final MasterService masterService;

    public MasterController(MasterService masterService) {
        this.masterService = masterService;
    }

    @GetMapping
    public ResponseEntity<List<MasterDto>> getAllMasters() {
        return ResponseEntity.ok(masterService.getAllMasters());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MasterDto> getMasterById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(masterService.getMasterById(id));
    }

    @PostMapping
    public ResponseEntity<MasterDto> createMaster(@RequestBody MasterDto masterDto) {
        return ResponseEntity.ok(masterService.createMaster(masterDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MasterDto> updateMaster(@PathVariable(value = "id") Long id, @RequestBody MasterDto masterDto) {
        return ResponseEntity.ok(masterService.updateMaster(id, masterDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaster(@PathVariable(value = "id") Long id) {
        masterService.deleteMaster(id);
        return ResponseEntity.noContent().build();
    }
}