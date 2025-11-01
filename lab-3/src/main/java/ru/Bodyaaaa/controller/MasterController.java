package ru.Bodyaaaa.controller;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import ru.Bodyaaaa.dto.MasterDTO;
import ru.Bodyaaaa.models.Master;
import ru.Bodyaaaa.service.MasterService;

import java.nio.file.AccessDeniedException;


@RestController
@RequestMapping("/api/master")
public class MasterController {

    private final MasterService masterService;

    public MasterController(MasterService masterService) {
        this.masterService = masterService;
    }

    @GetMapping("/")
    public ResponseEntity<Page<MasterDTO>> getAllMasters(
            @RequestParam(value = "age", required = false) Integer age,
            @RequestParam(value = "name", required = false) String name,
            Pageable pageable) {

        Page<Master> masters = masterService.search(age, name, pageable);
        Page<MasterDTO> masterDTOs = masters.map(masterService::convertToDto);

        return ResponseEntity.ok(masterDTOs);
    }


    @GetMapping("/{id}")
    public ResponseEntity<MasterDTO> getMaster(@PathVariable Integer id) {
        return ResponseEntity.ok(masterService.getMasterById(id));
    }

    @DeleteMapping("/delete-my-animal/{id}")
    public void deleteMyAnimal(@PathVariable("id") Integer id) throws AccessDeniedException {
        masterService.deleteMyAnimal(id);
    }
}