package ru.Bodyaaaa.service;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.Bodyaaaa.Roles.Role;
import ru.Bodyaaaa.Specifications.MasterSpecifications;
import ru.Bodyaaaa.dto.MasterDTO;
import ru.Bodyaaaa.models.Animal;
import ru.Bodyaaaa.models.Master;
import ru.Bodyaaaa.repository.AnimalRepository;
import ru.Bodyaaaa.repository.MasterRepository;
import org.springframework.data.jpa.domain.Specification;

import java.nio.file.AccessDeniedException;



@Service
public class MasterService {

    private final MasterRepository masterRepository;
    private final AnimalRepository animalRepository;
    private final ModelMapper modelMapper = new ModelMapper();
    private final PasswordEncoder passwordEncoder;

    public MasterService(MasterRepository masterRepository, AnimalRepository animalRepository, PasswordEncoder passwordEncoder) {
        this.masterRepository = masterRepository;
        this.animalRepository = animalRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Page<Master> search(Integer age, String name, Pageable pageable) {
        Specification<Master> spec = MasterSpecifications.withFilters(age, name);
        return masterRepository.findAll(spec, pageable);
    }


    public MasterDTO getMasterById(Integer id) {
        Master master = masterRepository.getMasterById(id)
                .orElseThrow(() -> new RuntimeException("Master not found"));
        return modelMapper.map(master, MasterDTO.class);
    }

    public MasterDTO convertToDto(Master master) {
        MasterDTO dto = new MasterDTO();
        dto.setId(master.getId());
        dto.setName(master.getName());
        dto.setAge(master.getAge());
        dto.setRole(master.getRole().name());
        dto.setUsername(master.getUsername());
        dto.setPassword(master.getPassword());
        return dto;
    }

    public void deleteMasterById(Integer id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthenticationCredentialsNotFoundException("Authentication required");
        }

        masterRepository.deleteById(id);
    }
    public MasterDTO save(MasterDTO masterDTO) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthenticationCredentialsNotFoundException("Authentication required");
        }

        Master master = new Master();
        master.setUsername(masterDTO.getUsername());
        master.setPassword(passwordEncoder.encode(masterDTO.getPassword()));
        master.setName(masterDTO.getName());
        master.setAge(masterDTO.getAge());
        master.setRole(Role.valueOf(masterDTO.getRole()));

        Master saved = masterRepository.save(master);

        MasterDTO result = new MasterDTO();
        result.setId(saved.getId());
        result.setUsername(saved.getUsername());
        result.setName(saved.getName());
        result.setAge(saved.getAge());
        result.setRole(saved.getRole().name());


        return result;
    }


    public boolean existsByUsername(String username) {
        return masterRepository.findMasterByUsername(username).isPresent();
    }

    public ResponseEntity<String> deleteMyAnimal(Integer id) throws AccessDeniedException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthenticationCredentialsNotFoundException("Authentication required");
        }

        String username = authentication.getName();

        Master currentMaster = masterRepository.findMasterByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Master not found: " + username));

        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Animal not found with id: " + id));

        Master animalOwner = animal.getMaster();


        boolean isOwner = animalOwner != null && animalOwner.getId().equals(currentMaster.getId());
        boolean isAdmin = currentMaster.getRole().name().equalsIgnoreCase("ADMIN");

        if (!isOwner && !isAdmin) {
            throw new AccessDeniedException("You are not allowed to delete this animal");
        }

        animalRepository.delete(animal);
        return null;
    }

}