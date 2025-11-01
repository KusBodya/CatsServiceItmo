package ru.Bodyaaaa.service;


import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.Bodyaaaa.Specifications.AnimalSpecifications;
import ru.Bodyaaaa.dto.AnimalDTO;

import ru.Bodyaaaa.models.Animal;
import ru.Bodyaaaa.models.Master;
import ru.Bodyaaaa.repository.AnimalRepository;
import ru.Bodyaaaa.repository.MasterRepository;

import java.util.List;

import java.util.stream.Collectors;

@Service
public class AnimalService {

    private final AnimalRepository animalRepository;
    private final ModelMapper modelMapper = new ModelMapper();
    private final MasterRepository masterRepository;

    public AnimalService(AnimalRepository animalRepository, MasterRepository masterRepository) {
        this.animalRepository = animalRepository;
        this.masterRepository = masterRepository;
    }

    public Page<Animal> search(String breed, Integer age, String name, Pageable pageable) {
        Specification<Animal> spec = AnimalSpecifications.withFilters(breed, age, name);
        return animalRepository.findAll(spec, pageable);
    }

    public AnimalDTO getAnimalById(Integer id) {
        return convertToDto(animalRepository.findById(id).orElse(null));
    }

    public AnimalDTO convertToDto(Animal animal) {
        AnimalDTO dto = modelMapper.map(animal, AnimalDTO.class);

        dto.setMasterId(animal.getMaster() != null ? animal.getMaster().getId() : null);

        dto.setFriendIds(animal.getFriends().stream()
                .map(Animal::getId)
                .collect(Collectors.toList()));

        return dto;
    }


    public AnimalDTO save(AnimalDTO animalDTO) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthenticationCredentialsNotFoundException("Authentication required");
        }

        Animal animal = new Animal();
        animal.setBreed(animalDTO.getBreed());
        animal.setName(animalDTO.getName());
        animal.setAge(animalDTO.getAge());
        animal.setTailLength(animalDTO.getTailLength());

        if (animalDTO.getMasterId() != null) {
            Master master = masterRepository.findById(animalDTO.getMasterId())
                    .orElseThrow(() -> new EntityNotFoundException("Master not found with id: " + animalDTO.getMasterId()));
            animal.setMaster(master);
        }

        List<Integer> friendIds = animalDTO.getFriendIds();
        if (friendIds != null && !friendIds.isEmpty()) {
            List<Animal> friends = animalRepository.findAllById(friendIds);


            if (friends.size() != friendIds.size()) {
                List<Integer> foundIds = friends.stream().map(Animal::getId).toList();
                List<Integer> missingIds = friendIds.stream()
                        .filter(id -> !foundIds.contains(id))
                        .collect(Collectors.toList());
                throw new EntityNotFoundException("Animals not found with ids: " + missingIds);
            }

            animal.setFriends(friends);
        }

        animal = animalRepository.save(animal);
        return convertToDto(animal);
    }


    public void deleteAnimalById(Integer id) {
        animalRepository.deleteAnimalById(id);
    }
}