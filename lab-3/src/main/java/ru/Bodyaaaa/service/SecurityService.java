package ru.Bodyaaaa.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.Bodyaaaa.dto.MasterDTO;
import ru.Bodyaaaa.models.Animal;
import ru.Bodyaaaa.repository.AnimalRepository;
import ru.Bodyaaaa.repository.MasterRepository;

@Service("securityService")
public class SecurityService {

    private final AnimalRepository animalRepository;
    private final MasterService masterService;

    public SecurityService(AnimalRepository animalRepository, MasterService masterService) {
        this.animalRepository = animalRepository;
        this.masterService = masterService;
    }

    public boolean isOwner(Authentication authentication, Integer animalId) {
        String username = authentication.getName();
        Animal animal = animalRepository
                .findById(animalId)
                .orElseThrow(() -> new EntityNotFoundException("Животное не найдено: " + animalId));
        String ownerUsername = animal.getMaster().getUsername();

        return username.equals(ownerUsername);
    }

    public boolean isUserId(Authentication authentication, Integer id) {
        String username = authentication.getName();
        MasterDTO master = masterService.getMasterById(id);
        return master.getUsername().equals(username);
    }
}

