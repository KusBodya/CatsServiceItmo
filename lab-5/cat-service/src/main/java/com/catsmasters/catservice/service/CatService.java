package com.catsmasters.catservice.service;

import com.catsmasters.catservice.dto.CatDto;
import com.catsmasters.catservice.model.Cat;
import com.catsmasters.catservice.repository.CatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CatService {

    private final CatRepository catRepository;

    private static final Logger log = LoggerFactory.getLogger(CatService.class);


    public CatService(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    public List<CatDto> getAllCats() {
        return catRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public CatDto getCatById(Long id) {
        Cat cat = catRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cat not found with id: " + id));
        return convertToDto(cat);
    }

    public List<CatDto> getCatsByOwner(Long ownerId) {
        return catRepository.findByOwnerId(ownerId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public CatDto createCat(CatDto catDto) {
        Cat cat = convertToEntity(catDto);
        Cat savedCat = catRepository.save(cat);
        log.info("Отправлено событие создания кота: {}", savedCat.getId());
        return convertToDto(savedCat);
    }

    public CatDto updateCat(Long id, CatDto catDto) {
        Cat existingCat = catRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cat not found with id: " + id));

        existingCat.setName(catDto.getName());
        existingCat.setBirthDate(catDto.getBirthDate());
        existingCat.setBreed(catDto.getBreed());
        existingCat.setColor(catDto.getColor());
        existingCat.setTailLength(catDto.getTailLength());
        existingCat.setOwnerId(catDto.getOwnerId());

        Cat updatedCat = catRepository.save(existingCat);
        return convertToDto(updatedCat);
    }

    public void deleteCat(Long id) {
        catRepository.deleteById(id);
    }

    private CatDto convertToDto(Cat cat) {
        CatDto dto = new CatDto();
        dto.setId(cat.getId());
        dto.setName(cat.getName());
        dto.setBirthDate(cat.getBirthDate());
        dto.setBreed(cat.getBreed());
        dto.setColor(cat.getColor());
        dto.setTailLength(cat.getTailLength());
        dto.setOwnerId(cat.getOwnerId());
        return dto;
    }

    private Cat convertToEntity(CatDto dto) {
        Cat cat = new Cat();
        cat.setName(dto.getName());
        cat.setBirthDate(dto.getBirthDate());
        cat.setBreed(dto.getBreed());
        cat.setColor(dto.getColor());
        cat.setTailLength(dto.getTailLength());
        cat.setOwnerId(dto.getOwnerId());
        return cat;
    }
}