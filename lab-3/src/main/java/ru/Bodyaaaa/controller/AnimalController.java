package ru.Bodyaaaa.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.Bodyaaaa.dto.AnimalDTO;
import ru.Bodyaaaa.models.Animal;
import ru.Bodyaaaa.service.AnimalService;

@RestController
@RequestMapping("/api/animal")
public class AnimalController {

    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @GetMapping("/")
    public ResponseEntity<Page<AnimalDTO>> getAllAnimals(
            @RequestParam(value = "breed", required = false) String breed,
            @RequestParam(value = "age", required = false) Integer age,
            @RequestParam(value = "name", required = false) String name,
            Pageable pageable) {

        Page<Animal> animals = animalService.search(breed, age, name, pageable);
        Page<AnimalDTO> animalDTOs = animals.map(animalService::convertToDto);

        return ResponseEntity.ok(animalDTOs);
    }


    @GetMapping("/{id}")
    public ResponseEntity<AnimalDTO> getAnimalById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(animalService.getAnimalById(id));
    }


    @PostMapping("/create-animal")
    public ResponseEntity<AnimalDTO> createAnimal(@RequestBody AnimalDTO animalDTO) {
        return ResponseEntity.ok(animalService.save(animalDTO));
    }

}