package com.catsmasters.gateway.controller;

import com.catsmasters.gateway.dto.CatDto;
import com.catsmasters.gateway.service.CatOrchestrationService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cats")
public class CatController {

    private final CatOrchestrationService catService;

    public CatController(CatOrchestrationService catService) {
        this.catService = catService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<CatDto>> getAllCats() {
        return ResponseEntity.ok(catService.getAllCats());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<CatDto> getCatById(@PathVariable Long id) {
        return ResponseEntity.ok(catService.getCatById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<CatDto> createCat(@RequestBody CatDto catDto) {
        return ResponseEntity.ok(catService.createCat(catDto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<CatDto> updateCat(@PathVariable Long id, @RequestBody CatDto catDto) {
        return ResponseEntity.ok(catService.updateCat(id, catDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<Void> deleteCat(@PathVariable Long id) {
        catService.deleteCat(id);
        return ResponseEntity.noContent().build();
    }
}