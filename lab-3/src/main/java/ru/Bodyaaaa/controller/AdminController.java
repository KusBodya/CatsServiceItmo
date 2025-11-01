package ru.Bodyaaaa.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.Bodyaaaa.dto.AnimalDTO;
import ru.Bodyaaaa.dto.MasterDTO;
import ru.Bodyaaaa.service.AnimalService;
import ru.Bodyaaaa.service.MasterService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AnimalService animalService;
    private final MasterService masterService;

    public AdminController(AnimalService animalService, MasterService masterService) {
        this.animalService = animalService;
        this.masterService = masterService;
    }

    @DeleteMapping("/delete-user/{id}")
    public void deleteUser(@PathVariable("id") Integer id) {
        masterService.deleteMasterById(id);
    }

    @DeleteMapping("/delete-animal/{id}")
    public void deleteAnimal(@PathVariable("id") Integer id) {
        animalService.deleteAnimalById(id);
    }

    @PostMapping("/add-animal")
    public ResponseEntity<AnimalDTO> addAnimal(@RequestBody AnimalDTO animalDTO) {
        return ResponseEntity.ok(animalService.save(animalDTO));
    }


    @GetMapping("/create-admin")
    public String registerPage1() {
        return "register";
    }

    @PostMapping("/create-admin")
    public ResponseEntity<MasterDTO> createMaster(@RequestBody MasterDTO masterDTO) {

        if (!masterService.existsByUsername(masterDTO.getUsername())) {
            return ResponseEntity.ok(masterService.save(masterDTO));
        }
        return null;
    }


}
