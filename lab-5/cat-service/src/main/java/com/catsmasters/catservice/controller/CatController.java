package com.catsmasters.catservice.controller;

import com.catsmasters.catservice.dto.CatDto;
import com.catsmasters.catservice.service.CatService;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cats")
public class CatController {

    private final CatService catService;

    public CatController(CatService catService) {
        this.catService = catService;
    }

    @GetMapping
    public ResponseEntity<List<CatDto>> getAllCats() {
        return ResponseEntity.ok(catService.getAllCats());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CatDto> getCatById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(catService.getCatById(id));
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<CatDto>> getCatsByOwner(@PathVariable(value = "ownerId") Long ownerId) {
        return ResponseEntity.ok(catService.getCatsByOwner(ownerId));
    }

    @PostMapping
    public ResponseEntity<CatDto> createCat(@RequestBody CatDto catDto) {
        return ResponseEntity.ok(catService.createCat(catDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CatDto> updateCat(@PathVariable(value = "id") Long id, @RequestBody CatDto catDto) {
        return ResponseEntity.ok(catService.updateCat(id, catDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCat(@PathVariable(value = "id") Long id) {
        try {
            catService.deleteCat(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}